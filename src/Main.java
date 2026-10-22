import ChessCore.Bishop;
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
        Position from = new Position(4, 3);

        Piece[] pieces = {
            new Rook(PieceColor.WHITE),
            new Bishop(PieceColor.WHITE),
            new Knight(PieceColor.WHITE),
            new Queen(PieceColor.WHITE),
            new King(PieceColor.WHITE),
            new Pawn(PieceColor.WHITE)
        };

        Position[] destinations = {
            new Position(4, 7),
            new Position(1, 6),
            new Position(2, 4),
            new Position(1, 3),
            new Position(3, 4),
            new Position(3, 3),
        };

        for(int i = 0; i < pieces.length; i++){
            System.out.println(pieces[i].getClass().getSimpleName() + ": " + pieces[i].isValidMove(from, destinations[i]));
        }
    }

}
