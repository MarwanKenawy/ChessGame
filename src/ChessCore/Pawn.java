package ChessCore;

public class Pawn extends Piece {
    
    public Pawn(PieceColor color){
        super(color);
    }

    @Override 
    public boolean isValidMove(Position from, Position to){

        if(from == null || to == null){
            return false;
        }

        int direction = getColor() == PieceColor.WHITE ? -1 : 1;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if(colDiff != 0){
            return false;
        }

        if(rowDiff == direction){
            return true;
        }

        int startRow = getColor() == PieceColor.WHITE ? 6 : 1;

        return from.getRow() == startRow && rowDiff == 2 * direction;
    }
}
