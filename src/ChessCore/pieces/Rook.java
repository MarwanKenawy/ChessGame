package ChessCore.pieces;

import ChessCore.board.Position;
import ChessCore.enums.PieceColor;

public class Rook extends Piece {
    public Rook(PieceColor color){
        super(color);
    }

    @Override 
    public boolean isValidMove(Position from, Position to){

        if(from == null || to == null){
            return false;
        }

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return (rowDiff == 0 && colDiff > 0) || (rowDiff > 0 && colDiff == 0);
    }
}
