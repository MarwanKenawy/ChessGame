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

        //E7 > E5
        game.makeMove(new Position(1, 4), new Position(3, 4));

        //G1 > F3
        game.makeMove(new Position(7, 6), new Position(5, 5));

        //B8 > C6
        game.makeMove(new Position(0, 1), new Position(2, 2));

        //F1 > C4
        game.makeMove(new Position(7, 5), new Position(4, 2));

        //G8 > F6
        game.makeMove(new Position(0, 6), new Position(2, 5));

        Position e1 = new Position(7, 4);
        Position g1 = new Position(7, 6);

        System.out.println("Can castle: " + game.isValidMove(e1, g1));

        //O-O
        game.makeMove(e1, g1);

        System.out.println("King at G1: " + game.getPiece(g1).getClass().getSimpleName());

        System.out.println("Rook at F1: " + game.getPiece(new Position(7, 5)).getClass().getSimpleName());

        System.out.println("Current Turn: " + game.getCurrentTurn());
    }

}
