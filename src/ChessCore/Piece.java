package ChessCore;

public abstract class Piece{
    private final PieceColor color;

    public Piece(PieceColor color){
        if(color == null){
            throw new IllegalArgumentException("Piece color cannot be null");
        }

        this.color = color;
    }

    public PieceColor getColor(){
        return color;
    }

    public abstract boolean isValidMove(Position from, Position to);
}