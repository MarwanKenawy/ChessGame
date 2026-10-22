import ChessCore.PieceColor;
import ChessCore.Position;

public class Main {
    public static void main(String[] args){
        System.out.println("Chess Game");
        System.out.println("Chess Core");
        
        PieceColor color = PieceColor.WHITE;
        Position position = new Position(6, 4);
    
        System.out.println("Piece color: " + color);
        System.out.println("Row: " + position.getRow());
        System.out.println("Col: " + position.getCol());
    }

}
