import ChessCore.Bishop;
import ChessCore.Board;
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
        Board board = new Board();

        Position whiteKing = new Position(7, 4);
        Position blackQueen = new Position(0, 3);
        Position whitePawn = new Position(6, 4);
        Position emptySquare = new Position(4, 4);

        Piece piece1 = board.getPiece(whiteKing);
        Piece piece2 = board.getPiece(blackQueen);
        Piece piece3 = board.getPiece(whitePawn);

        System.out.println("White King: " +
            piece1.getClass().getSimpleName());

        System.out.println("Black Queen: " +
            piece2.getClass().getSimpleName());

        System.out.println("White Pawn: " +
            piece3.getClass().getSimpleName());

        System.out.println("Empty Square: " +
            board.isEmpty(emptySquare));

        System.out.println("White King Color: " +
            piece1.getColor());
    }

}
