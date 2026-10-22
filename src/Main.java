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

        //fool's mate
        //F2 > F3
        game.makeMove(new Position(6, 5), new Position(5, 5));

        //E7 > E5
        game.makeMove(new Position(1, 4), new Position(3, 4));

        //G2 > G4
        game.makeMove(new Position(6, 6), new Position(4, 6));

        //D8 > H4
        game.makeMove(new Position(0, 3), new Position(4, 7));

        System.out.println("White in check: " + game.isKingInCheck(PieceColor.WHITE));

        System.out.println("Black in check: " + game.isKingInCheck(PieceColor.BLACK));

        System.out.println("Current Turn: " + game.getCurrentTurn());

        //try unrelated move while in check
        try {
            game.makeMove(new Position(6, 0), new Position(5, 0));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
