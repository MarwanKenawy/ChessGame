import ChessCore.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        ChessGame game = new ChessGame();

        try(BufferedReader reader = new BufferedReader(new FileReader("ChessGame.txt"))){
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if(line.isEmpty()){
                    continue;
                }

                try{
                    String[] parts = line.split(",", -1);

                    if(parts.length != 2 && parts.length != 3){
                        throw new IllegalArgumentException("Invalid move");
                    }

                    Position from = Position.fromChessNotation(parts[0].trim());
                    Position to = Position.fromChessNotation(parts[1].trim());

                    if(parts.length == 3){
                        String choice = parts[2].trim();

                        if(choice.length() != 1){
                            throw new IllegalArgumentException("Invalid move");
                        }
                        game.makeMove(from, to, choice.charAt(0));
                    }else{
                        game.makeMove(from, to);
                    }

                    MoveEvent event = game.getLastMoveEvent();

                    if(event == MoveEvent.CASTLE){
                        System.out.println("Castle");
                    }else if(event == MoveEvent.EN_PASSANT){
                        System.out.println("Enpassant");
                    }else if(event == MoveEvent.CAPTURE){
                        System.out.println("Capture");
                    }

                    if(game.getGameStatus() == GameStatus.IN_PROGRESS && game.isKingInCheck(game.getCurrentTurn())){
                        if(game.getCurrentTurn() == PieceColor.WHITE){
                            System.out.println("White in check");
                        }else{
                            System.out.println("Black in check");
                        }
                    }

                    switch (game.getGameStatus()) {
                        case WHITE_WON:
                            System.out.println("White Won");
                            break;
                        case BLACK_WON:    
                            System.out.println("Black Won");
                            break;
                        case STALEMATE:    
                            System.out.println("Stalemate");
                            break;
                        case INSUFFICIENT_MATERIAL:    
                            System.out.println("Insufficient Material");
                            break;
                        default:
                            break;
                    }
                }catch(IllegalArgumentException | IllegalStateException e){
                    System.out.println(e);
                }
            }
        }catch(IOException e){
            System.err.println("Error reading ChessGame.txt: " + e.getMessage());
        }

        // // Test 1: King vs King
        // Board board1 = new Board(false);

        // board1.setPiece(new Position(7, 4), new King(PieceColor.WHITE));

        // board1.setPiece(new Position(0, 4), new King(PieceColor.BLACK));

        // ChessGame game1 = new ChessGame(board1);

        // System.out.println("King vs King: " + game1.isInsufficientMaterial());

        // // Test 2: King + Bishop vs King
        // Board board2 = new Board(false);

        // board2.setPiece(new Position(7, 4),new King(PieceColor.WHITE));

        // board2.setPiece(new Position(0, 4), new King(PieceColor.BLACK));

        // board2.setPiece(new Position(6, 2), new Bishop(PieceColor.WHITE));

        // ChessGame game2 = new ChessGame(board2);

        // System.out.println("King + Bishop vs King: " + game2.isInsufficientMaterial());

        // // Test 3: King + Rook vs King
        // Board board3 = new Board(false);

        // board3.setPiece(new Position(7, 4), new King(PieceColor.WHITE));

        // board3.setPiece(new Position(0, 4), new King(PieceColor.BLACK));

        // board3.setPiece(new Position(6, 0), new Rook(PieceColor.WHITE));

        // ChessGame game3 = new ChessGame(board3);

        // System.out.println("King + Rook vs King: " + game3.isInsufficientMaterial());
    }
}
