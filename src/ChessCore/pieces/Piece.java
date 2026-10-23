package ChessCore.pieces;

import ChessCore.board.Position;
import ChessCore.enums.PieceColor;

public abstract class Piece{
    private final PieceColor color;
    private boolean hasMoved = false;

    public Piece(PieceColor color){
        if(color == null){
            throw new IllegalArgumentException("Piece color cannot be null");
        }

        this.color = color;
    }

    public PieceColor getColor(){
        return color;
    }

    public boolean hasMoved(){
        return hasMoved;
    }

    public void markAsMoved(){
        hasMoved = true;
    }

    public abstract boolean isValidMove(Position from, Position to);
}