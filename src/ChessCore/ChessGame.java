package ChessCore;

public class ChessGame {
    private final Board board;
    private PieceColor currentTurn;

    public ChessGame(){
        board = new Board();
        currentTurn = PieceColor.WHITE;
    }

    public PieceColor getCurrentTurn(){
        return currentTurn;
    }

    public Piece getPiece(Position position){
        return board.getPiece(position);
    }

    public boolean isValidMove(Position from, Position to){
        if(from == null || to == null){
            return false;
        }

        Piece piece = board.getPiece(from);

        if(piece == null){
            return false;
        }

        if(piece.getColor() != currentTurn){
            return false;
        }

        Piece destinationPiece = board.getPiece(to);

        if(destinationPiece != null && destinationPiece.getColor() == currentTurn){
            return false;
        }

        if(destinationPiece instanceof King){
            return false;
        }

        return piece.isValidMove(from, to);
    }

    public void makeMove(Position from, Position to){
        if(!isValidMove(from, to)){
            throw new IllegalArgumentException("Invalid move");
        }

        Piece piece = board.getPiece(from);

        board.setPiece(to, piece);
        board.setPiece(from, null);

        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
