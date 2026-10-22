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

        Position a1 = new Position(7, 0);
        Position a4 = new Position(4, 0);

        System.out.println("Rook A1 -> A4: " + game.isValidMove(a1, a4));

        Position e2 = new Position(6, 4);
        Position e4 = new Position(4, 4);

        System.out.println("Pawn E2 -> E4: " + game.isValidMove(e2, e4));

        game.makeMove(e2, e4);

        Position d7 = new Position(1, 3);
        Position d5 = new Position(3, 3);

        game.makeMove(d7, d5);

        System.out.println("Pawn E4 -> D5: " + game.isValidMove(e4, d5));

        game.makeMove(e4, d5);

        Piece piece = game.getPiece(d5);

        System.out.println("Piece at D5: " + piece.getClass().getSimpleName());

        System.out.println("Piece Color: " + piece.getColor());

        System.out.println("Current Turn: " + game.getCurrentTurn());
    }

}
