import ChessCore.ChessGame;
import ChessCore.board.*;
import ChessCore.pieces.*;
import ChessCore.enums.*;

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
                        System.out.println("Captured " + game.getLastCapturedPieceName());
                    }

                    GameStatus status = game.getGameStatus();
                    if(status == GameStatus.WHITE_WON){
                        System.out.println("White Won");
                    }else if(status == GameStatus.BLACK_WON){
                        System.out.println("Black Won");
                    }else{
                        if(game.isKingInCheck(game.getCurrentTurn())){
                            if(game.getCurrentTurn() == PieceColor.WHITE){
                                System.out.println("White in check");
                            }else{
                                System.out.println("Black in check");
                            }
                        }
                        
                        if(status == GameStatus.STALEMATE){
                            System.out.println("Stalemate");
                        }else if(status == GameStatus.INSUFFICIENT_MATERIAL){
                            System.out.println("Insufficient Material");
                        }
                    }
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid move");
                }catch(IllegalStateException e){
                    System.out.println(e.getMessage());
                }
            }
        }catch(IOException e){
            System.err.println("Error reading ChessGame.txt: " + e.getMessage());
        }
    }
}
