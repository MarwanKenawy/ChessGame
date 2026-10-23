package ChessCore.pieces;

import ChessCore.board.Position;
import ChessCore.enums.PieceColor;

public class Queen extends Piece {
    
    public Queen(PieceColor color){
        super(color);
    }

    @Override 
    public boolean isValidMove(Position from, Position to){
        
        if(from == null || to == null){
            return false;
        }

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        boolean straight = (rowDiff == 0 && colDiff > 0) || (rowDiff > 0 && colDiff == 0);
        boolean diagonal = rowDiff == colDiff && rowDiff > 0;

        return straight || diagonal;
    }
}
