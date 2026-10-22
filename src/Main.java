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

        System.out.println("Initial Turn: " +game.getCurrentTurn());

        //white pawn E2 > E4
        Position e2 = new Position(6, 4);
        Position e4 = new Position(4, 4);

        game.makeMove(e2, e4);

        System.out.println("After White Move: " + game.getCurrentTurn());

        //black pawn E7 > E5
        Position e7 = new Position(1, 4);
        Position e5 = new Position(3, 4);

        game.makeMove(e7, e5);

        System.out.println("After Black Move: " + game.getCurrentTurn());


        try {
            game.makeMove(e5, new Position(4, 4));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Final Turn: " + game.getCurrentTurn());
    }

}
