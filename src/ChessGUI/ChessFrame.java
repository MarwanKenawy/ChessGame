package ChessGUI;

import ChessCore.ChessGame;
import ChessCore.board.Position;
import ChessCore.enums.GameStatus;
import ChessCore.enums.PieceColor;
import ChessCore.pieces.*;

import javax.swing.*;
import java.awt.*;

public class ChessFrame extends JFrame {
    
    private final ChessGame game;
    private final JButton[][] squares = new JButton[8][8];

    private final Color lightSquare = new Color(240, 217, 181);
    private final Color darkSquare = new Color(181, 136, 99);

    private Position selectedPosition = null;
    private java.util.List<Position> legalMoves = new java.util.ArrayList<>();
    private final JLabel turnLabel = new JLabel();
    private boolean boardFlipped = false;
    private final JButton undoButton = new JButton("Undo");

    public ChessFrame(){

        game = new ChessGame();

        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                JButton square = new JButton();
                square.setFont(new Font("Serif", Font.PLAIN, 42));

                square.setFocusPainted(false);
                square.setOpaque(true);
                square.setBorderPainted(false);

                square.setBackground((row + col) % 2 == 0 ? lightSquare : darkSquare);
                squares[row][col] = square;

                final int r = row;
                final int c = col;

                square.addActionListener(e -> handleSquareClick(r, c));

                boardPanel.add(square);
            }
        }

        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(turnLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(undoButton);
        add(bottomPanel, BorderLayout.SOUTH);
        undoButton.addActionListener(e -> undoMove());

        refreshBoard();

        setSize(640, 640);
        setMinimumSize(new Dimension(480, 480));
        setLocationRelativeTo(null);        
    }

    private void refreshBoard(){

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col ++){

                Position position = getBoardPosition(row, col);
                Piece piece = game.getPiece(position);

                squares[row][col].setText(getPieceSymbol(piece));

                Color squareColor = (row + col) % 2 == 0 ? lightSquare : darkSquare;
   
                if(isKingInCheckAt(position)){
                    squareColor = Color.RED;
                }

                if(selectedPosition != null && selectedPosition.getRow() == position.getRow() && selectedPosition.getCol() == position.getCol()){
                    squareColor = new Color(246, 246, 105);  //yellow
                }else{
                    for(Position move : legalMoves){
                        if(move.getRow() == position.getRow() && move.getCol() == position.getCol()){
                            squareColor = new Color(130, 200, 100);  //green
                            break;
                        }
                    }
                }

                squares[row][col].setBackground(squareColor);
            }
        }

        turnLabel.setText(game.getCurrentTurn() == PieceColor.WHITE ? "White to move" : "Black to move");

        if(game.getGameStatus() != GameStatus.IN_PROGRESS){
            String message = game.getGameStatus().toString().replace("_", " ");
            JOptionPane.showMessageDialog(this, "Game over: " + message, "Game Finished", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getPieceSymbol(Piece piece){
        if(piece == null){
            return "";
        }

        boolean white = piece.getColor() == PieceColor.WHITE;

        if (piece instanceof King) {
            return white ? "♔" : "♚";
        }

        if (piece instanceof Queen) {
            return white ? "♕" : "♛";
        }

        if (piece instanceof Rook) {
            return white ? "♖" : "♜";
        }

        if (piece instanceof Bishop) {
            return white ? "♗" : "♝";
        }

        if (piece instanceof Knight) {
            return white ? "♘" : "♞";
        }

        if (piece instanceof Pawn) {
            return white ? "♙" : "♟";
        }

        return "";
    }

    private void handleSquareClick(int row, int col){
        Position clickedPosition = getBoardPosition(row, col);
        Piece clickedPiece = game.getPiece(clickedPosition);

        if(selectedPosition == null){
            if(clickedPiece != null && clickedPiece.getColor() == game.getCurrentTurn()){
                selectedPosition = clickedPosition;
                legalMoves = game.getAllValidMovesFromSquare(selectedPosition);
                refreshBoard();
            }
            return;
        }
        
        if(selectedPosition.getRow() == clickedPosition.getRow() && selectedPosition.getCol() == clickedPosition.getCol()){
            selectedPosition = null;
            legalMoves.clear();
            refreshBoard();
            return;
        }

        if(clickedPiece != null && clickedPiece.getColor() == game.getCurrentTurn()){
            selectedPosition = clickedPosition;
            legalMoves = game.getAllValidMovesFromSquare(selectedPosition);
            refreshBoard();
            return;
        }

        for(Position move : legalMoves){
            if(move.getRow() == clickedPosition.getRow() && move.getCol() == clickedPosition.getCol()){
                try{
                    game.makeMove(selectedPosition, clickedPosition);
                    boardFlipped = !boardFlipped;

                    selectedPosition = null;
                    legalMoves.clear();
                }catch(IllegalArgumentException | IllegalStateException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Move", JOptionPane.WARNING_MESSAGE);
                }

                refreshBoard();
                return;
            }
        }
    }

    private boolean isKingInCheckAt(Position position){
        Piece piece = game.getPiece(position);

        return piece instanceof King && game.isKingInCheck(piece.getColor());
    }

    private Position getBoardPosition(int displayRow, int displayCol){
        if(boardFlipped){
            return new Position(7 - displayRow, 7 - displayCol);
        }

        return new Position(displayRow, displayCol);
    }

    private void undoMove(){
        boolean undone = game.undoLastMove();

        if(!undone){
            JOptionPane.showMessageDialog(this, "There are no moves to undo.", "Undo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        selectedPosition = null;
        legalMoves.clear();

        boardFlipped = !boardFlipped;

        refreshBoard();
    }
}
