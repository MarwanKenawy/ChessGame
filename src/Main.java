import ChessCore.Bishop;
import ChessCore.Board;
import ChessCore.ChessGame;
import ChessCore.King;
import ChessCore.Knight;
import ChessCore.Pawn;
import ChessCore.Piece;
import ChessCore.PieceColor;
import ChessCore.Position;
import ChessCore.Queen;
import ChessCore.Rook;

public class Main {
    public static void main(String[] args){

        ChessGame game = new ChessGame();

        //E2 > E4
        game.makeMove(new Position(6, 4), new Position(4, 4));

        //A7 > A6
        game.makeMove(new Position(1, 0), new Position(2, 0));

        //E4 > E5
        game.makeMove(new Position(4, 4), new Position(3, 4));

        //D7 > D5
        game.makeMove(new Position(1, 3), new Position(3, 3));

        Position e5 = new Position(3, 4);
        Position d6 = new Position(2, 3);
        Position d5 = new Position(3, 3);

        System.out.println("En passant valid: " + game.isValidMove(e5, d6));

        //E5 > D6 (en passant)
        game.makeMove(e5, d6);

        Piece whitePawn = game.getPiece(d6);

        System.out.println("Piece at D6: " + whitePawn.getClass().getSimpleName());

        System.out.println("Piece Color: " + whitePawn.getColor());

        System.out.println("D5 is empty: " + (game.getPiece(d5) == null));

        System.out.println("Current Turn: " + game.getCurrentTurn());
    }

}
