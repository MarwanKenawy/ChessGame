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

        if(piece instanceof Pawn){
            return isValidPawnMove(from, to, piece.getColor());
        }

        if(!piece.isValidMove(from, to)){
            return false;
        }

        if(piece instanceof Knight){
            return true;
        }

        return isPathClear(from, to);
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

    private boolean isPathClear(Position from, Position to){

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        int rowStep = Integer.compare(rowDiff, 0);
        int colStep = Integer.compare(colDiff, 0);

        int currentRow = from.getRow() + rowStep;
        int currentCol = from.getCol() + colStep;

        while(currentRow != to.getRow() || currentCol != to.getCol()){

            Position current = new Position(currentRow, currentCol);

            if(!board.isEmpty(current)){
                return false;
            }

            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    private boolean isValidPawnMove(Position from, Position to, PieceColor color){
        int direction = color == PieceColor.WHITE ? -1 : 1;
        int startRow = color == PieceColor.WHITE ? 6 : 1;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        Piece destinationPiece = board.getPiece(to);

        if(colDiff == 0 && rowDiff == direction){
            return destinationPiece == null;
        }

        if(colDiff == 0 && rowDiff == 2 * direction && from.getRow() == startRow){
            Position intermediate = new Position(from.getRow() + direction, from.getCol());
            return board.isEmpty(intermediate) && destinationPiece == null;
        }

        if(Math.abs(colDiff) == 1 && rowDiff == direction){
            return destinationPiece != null && destinationPiece.getColor() != color;
        }

        return false;
    }
}
