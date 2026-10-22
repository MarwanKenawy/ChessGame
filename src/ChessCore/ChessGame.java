package ChessCore;

public class ChessGame {
    private final Board board;
    private PieceColor currentTurn;
    private GameStatus gameStatus;

    public ChessGame(){
        board = new Board();
        currentTurn = PieceColor.WHITE;
        gameStatus = GameStatus.IN_PROGRESS;
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    public PieceColor getCurrentTurn(){
        return currentTurn;
    }

    public Piece getPiece(Position position){
        return board.getPiece(position);
    }

    public boolean isPseudoLegalMove(Position from, Position to){
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

    public boolean isValidMove(Position from, Position to){

        if(!isPseudoLegalMove(from, to)){
            return false;
        }

        Piece movingPiece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);

        board.setPiece(to, movingPiece);
        board.setPiece(from, null);

        boolean kingInCheck;

        try{
            kingInCheck = isKingInCheck(currentTurn);
        } finally {
            board.setPiece(from, movingPiece);
            board.setPiece(to, capturedPiece);
        }

        return !kingInCheck;
    }

    public void makeMove(Position from, Position to){

        if(gameStatus != GameStatus.IN_PROGRESS){
            throw new IllegalStateException("Game already ended");
        }

        if(!isValidMove(from, to)){
            throw new IllegalArgumentException("Invalid move");
        }

        Piece piece = board.getPiece(from);

        board.setPiece(to, piece);
        board.setPiece(from, null);

        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        if(isCheckmate()){
            gameStatus = currentTurn == PieceColor.WHITE ? GameStatus.BLACK_WON : GameStatus.WHITE_WON;
        }else if(isStalemate()){
            gameStatus = GameStatus.STALEMATE;
        }
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

    private boolean isSquareAttacked(Position square, PieceColor attackerColor){
        
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){

                Position from = new Position(row, col);
                Piece piece = board.getPiece(from);

                if(piece == null || piece.getColor() != attackerColor){
                    continue;
                }

                if(piece instanceof Pawn){

                    int direction = attackerColor == PieceColor.WHITE ? -1 : 1;

                    int rowDiff = square.getRow() - row;
                    int colDiff = Math.abs(square.getCol() - col);

                    if(rowDiff == direction && colDiff == 1){
                        return true;
                    }

                    continue;
                }

                if(!piece.isValidMove(from, square)){
                    continue;
                }

                if(piece instanceof Knight){
                    return true;
                }

                if(isPathClear(from, square)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isKingInCheck(PieceColor color){

        if(color == null){
            throw new IllegalArgumentException("King color cannot be null");
        }

        Position kingPosition = null;

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){

                Position position = new Position(row, col);
                Piece piece = board.getPiece(position);

                if(piece instanceof King && piece.getColor() == color){
                    kingPosition = position;
                    break;
                }
            }

            if(kingPosition != null){
                break;
            }
        }

        if(kingPosition == null){
            throw new IllegalArgumentException("King not found");
        }

        PieceColor opponent = color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        return isSquareAttacked(kingPosition, opponent);
    }

    public java.util.List<Position> getAllValidMovesFromSquare(Position from){

        java.util.List<Position> validMoves = new java.util.ArrayList<>();

        if(from == null){
            return validMoves;
        }

        Piece piece = board.getPiece(from);

        if(piece == null || piece.getColor() != currentTurn){
            return validMoves;
        }

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){

                Position to = new Position(row, col);

                if(isValidMove(from, to)){
                    validMoves.add(to);
                }
            }
        }

        return validMoves;
    }

    private boolean hasAnyValidMoves(){

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){

                Position from = new Position(row, col);

                if(!getAllValidMovesFromSquare(from).isEmpty()){
                    return true;
                }

            }
        }

        return false;
    }

    public boolean isCheckmate(){
        return isKingInCheck(currentTurn) && !hasAnyValidMoves();
    }

    public boolean isStalemate(){
        return !isKingInCheck(currentTurn) && !hasAnyValidMoves();
    }
}
