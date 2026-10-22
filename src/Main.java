import ChessCore.PieceColor;
import ChessCore.Position;
import ChessCore.Rook;

public class Main {
    public static void main(String[] args){
        Rook rook = new Rook(PieceColor.WHITE);

        Position from = new Position(4, 3);

        Position move1 = new Position(4, 7); 
        Position move2 = new Position(0, 3); 
        Position move3 = new Position(6, 5); 

        System.out.println("Rook color: " + rook.getColor());
        System.out.println("move1 : " + rook.isValidMove(from, move1));
        System.out.println("move2 : " + rook.isValidMove(from, move2));
        System.out.println("move3 : " + rook.isValidMove(from, move3));
    }

}
