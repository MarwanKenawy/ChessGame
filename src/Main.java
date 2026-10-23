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

        //A2 > A4
        game.makeMove(new Position(6, 0), new Position(4, 0));

        //B7 > B5
        game.makeMove(new Position(1, 1), new Position(3, 1));

        //A4 > B5 (capture)
        game.makeMove(new Position(4, 0), new Position(3, 1));

        //A7 > A6
        game.makeMove(new Position(1, 0), new Position(2, 0));

        //B5 > B6
        game.makeMove(new Position(3, 1), new Position(2, 1));

        //H7 > H6
        game.makeMove(new Position(1, 7), new Position(2, 7));

        //B6 > C7 (capture)
        game.makeMove(new Position(2, 1), new Position(1, 2));

        //H6 > H5
        game.makeMove(new Position(2, 7), new Position(3, 7));

        Position c7 = new Position(1, 2);
        Position b8 = new Position(0, 1);

        //C7 > B8 = Queen (capture and promote)
        System.out.println("Promotion move valid: " + game.isValidMove(c7, b8));

        // game.makeMove(c7, b8, 'Q');
        try {
            game.makeMove(c7, b8, 'X');
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Pawn still at C7: " + (game.getPiece(c7) instanceof Pawn));

        Piece promotedPiece = game.getPiece(b8);

        System.out.println("Promoted piece: " + promotedPiece.getClass().getSimpleName());

        System.out.println("Piece color: " + promotedPiece.getColor());

        System.out.println("C7 is empty: " + (game.getPiece(c7) == null));

        System.out.println("Current Turn: " + game.getCurrentTurn());
    }
}
