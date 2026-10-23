package ChessCore.pieces;

import ChessCore.board.Position;
import ChessCore.enums.PieceColor;

public class Knight extends Piece {
    
    public Knight(PieceColor color){
        super(color);
    }

    @Override 
    public boolean isValidMove(Position from, Position to){
        
        if(from == null || to == null){
            return false;
        }

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}
