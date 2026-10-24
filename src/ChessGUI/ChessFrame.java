package ChessGUI;

import ChessCore.ChessGame;
import ChessCore.board.Position;
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

        add(boardPanel, BorderLayout.CENTER);

        refreshBoard();

        setSize(640, 640);
        setMinimumSize(new Dimension(480, 480));
        setLocationRelativeTo(null);        
    }

    private void refreshBoard(){

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col ++){

                Position position = new Position(row, col);
                Piece piece = game.getPiece(position);

                squares[row][col].setText(getPieceSymbol(piece));

                Color squareColor = (row + col) % 2 == 0 ? lightSquare : darkSquare;

                if(selectedPosition != null && selectedPosition.getRow() == row && selectedPosition.getCol() == col){
                    squareColor = new Color(246, 246, 105);
                }else{
                    for(Position move : legalMoves){
                        if(move.getRow() == row && move.getCol() == col){
                            squareColor = new Color(130, 200, 100);
                            break;
                        }
                    }
                }

                squares[row][col].setBackground(squareColor);
            }
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
        Position clickedPosition = new Position(row, col);
        Piece clickedPiece = game.getPiece(clickedPosition);

        if(selectedPosition == null){
            if(clickedPiece != null && clickedPiece.getColor() == game.getCurrentTurn()){
                selectedPosition = clickedPosition;
                legalMoves = game.getAllValidMovesFromSquare(selectedPosition);
                refreshBoard();
            }
            return;
        }
        
        if(selectedPosition.getRow() == row && selectedPosition.getCol() == col){
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
            if(move.getRow() == row && move.getCol() == col){
                try{
                    game.makeMove(selectedPosition, clickedPosition);

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
}
