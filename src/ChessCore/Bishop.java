package ChessCore;

public class Bishop extends Piece {
    public Bishop(PieceColor color){
        super(color);
    }

    @Override 
    public boolean isValidMove(Position from, Position to){
        
        if(from == null || to == null){
            return false;
        }

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return rowDiff == colDiff && rowDiff > 0;
    }
}
