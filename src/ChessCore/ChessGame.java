package ChessCore;

public class ChessGame {
    private final Board board;
    private PieceColor currentTurn;
    private GameStatus gameStatus;
    private Position enPassantTarget;

    public ChessGame(){
        board = new Board();
        currentTurn = PieceColor.WHITE;
        gameStatus = GameStatus.IN_PROGRESS;
        enPassantTarget = null;
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

        if(from == null || to == null){
            return false;
        }

        Piece piece = board.getPiece(from);

        if(piece == null || piece.getColor() != currentTurn){
            return false;
        }

        if(piece instanceof King && from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) == 2){
            return isValidCastling(from, to);
        }

        boolean enPassant = isValidEnPassant(from, to);

        if(!enPassant && ! isPseudoLegalMove(from, to)){
            return false;
        }


        // if(!isPseudoLegalMove(from, to)){
        //     return false;
        // }

        Piece capturedPiece = board.getPiece(to);

        Position enPassantCapturedPosition = null;
        Piece enPassantCapturedPiece = null;

        if(enPassant){
            enPassantCapturedPosition = new Position(from.getRow(), to.getCol());
            enPassantCapturedPiece = board.getPiece(enPassantCapturedPosition);

            board.setPiece(enPassantCapturedPosition, null);
        }

        board.setPiece(to, piece);
        board.setPiece(from, null);

        boolean kingInCheck;

        try{
            kingInCheck = isKingInCheck(currentTurn);
        } finally {
            board.setPiece(from, piece);
            board.setPiece(to, capturedPiece);

            if(enPassant){
                board.setPiece(enPassantCapturedPosition, enPassantCapturedPiece);
            }
        }

        return !kingInCheck;
    }

    public void makeMove(Position from, Position to){
        makeMove(from, to, 'Q');
    }

    public void makeMove(Position from, Position to, char promotionChoice){

        if(gameStatus != GameStatus.IN_PROGRESS){
            throw new IllegalStateException("Game already ended");
        }

        if(!isValidMove(from, to)){
            throw new IllegalArgumentException("Invalid move");
        }

        boolean promotion = isPromotionMove(from, to);
        Piece promotionPiece = null;

        if(promotion){
            promotionPiece = createPromotionPiece(promotionChoice, currentTurn);
        }

        Piece piece = board.getPiece(from);

        boolean castling = piece instanceof King && Math.abs(from.getCol() - to.getCol()) == 2;

        boolean enPassant = isValidEnPassant(from, to);
        enPassantTarget = null;
        
        if(castling){

            int row = from.getRow();
            boolean kingSide = to.getCol() == 6;

            int rookFromCol = kingSide ? 7 : 0;
            int rookToCol = kingSide ? 5 : 3;

            Position rookFrom = new Position(row, rookFromCol);
            Position rookTo = new Position(row, rookToCol);

            Piece rook = board.getPiece(rookFrom);

            board.setPiece(to, piece);
            board.setPiece(from, null);
            
            board.setPiece(rookTo, rook);
            board.setPiece(rookFrom, null);

            rook.markAsMoved();;
        }else{

            if(enPassant){
                Position capturedPosition = new Position(from.getRow(), to.getCol());
                board.setPiece(capturedPosition, null);
            }

            board.setPiece(to, promotion ? promotionPiece : piece);
            board.setPiece(from, null);

            if(piece instanceof Pawn && Math.abs(to.getRow() - from.getRow()) == 2){
                int middleRow = (from.getRow() + to.getRow()) / 2;

                enPassantTarget = new Position(middleRow, from.getCol());
            }
        }

        piece.markAsMoved();

        if(promotion){
            promotionPiece.markAsMoved();
        }

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

    private boolean isValidCastling(Position from, Position to){

        Piece king = board.getPiece(from);

        if(!(king instanceof King) || king.hasMoved()){
            return false;
        }

        int row = king.getColor() == PieceColor.WHITE ? 7 : 0;

        if(from.getRow() != row || from.getCol() != 4){
            return false;
        }

        if(to.getRow() != row){
            return false;
        }

        if(to.getCol() != 2 && to.getCol() != 6){
            return false;
        }

        boolean kingSide = to.getCol() == 6;

        int rookCol = kingSide ? 7 : 0;

        Position rookPosition = new Position(row, rookCol);
        Piece rook = board.getPiece(rookPosition);

        if(!(rook instanceof Rook) || rook.getColor() != king.getColor() || rook.hasMoved()){
            return false;
        }

        int step = kingSide ? 1 : -1;

        for(int col = 4 + step; col != rookCol; col += step){
            if(!board.isEmpty(new Position(row, col))){
                return false;
            }
        }

        PieceColor opponent = king.getColor() == PieceColor.WHITE? PieceColor.BLACK : PieceColor.WHITE;

        if(isSquareAttacked(from, opponent)){
            return false;
        }

        Position intermediate = new Position(row, 4 + step);

        Piece original = board.getPiece(from);

        board.setPiece(from, null);

        try{
            if(isSquareAttacked(intermediate, opponent) || isSquareAttacked(to, opponent)){
                return false;
            }
        }finally{
            board.setPiece(from, original);
        }

        return true;
    }

    private boolean isValidEnPassant(Position from, Position to){
        Piece piece = board.getPiece(from);

        if(!(piece instanceof Pawn)){
            return false;
        }

        if(enPassantTarget == null){
            return false;
        }

        if(to.getRow() != enPassantTarget.getRow() || to.getCol() != enPassantTarget.getCol()){
            return false;
        }

        int direction = piece.getColor() == PieceColor.WHITE ? -1 : 1;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getCol() - from.getCol());

        if(rowDiff != direction || colDiff != 1){
            return false;
        }

        if(!board.isEmpty(to)){
            return false;
        }

        Position capturedPosition = new Position(from.getRow(), to.getCol());
        Piece capturedPiece = board.getPiece(capturedPosition);

        return capturedPiece instanceof Pawn && capturedPiece.getColor() != piece.getColor();
    }

    private Piece createPromotionPiece(char promotionChoice, PieceColor color){
        switch (Character.toUpperCase(promotionChoice)) {
            case 'Q':
                return new Queen(color);
            case 'R':
                return new Rook(color);
            case 'B':
                return new Bishop(color);
            case 'K':
                return new Knight(color);
            default:
                throw new IllegalArgumentException("Invalid promotion piece");
        }
    }

    private boolean isPromotionMove(Position from, Position to){
        if(from == null || to == null){
            return false;
        }

        Piece piece = board.getPiece(from);

        if(!(piece instanceof Pawn)){
            return false;
        }

        return (piece.getColor() == PieceColor.WHITE && to.getRow() == 0) || (piece.getColor() == PieceColor.BLACK && to.getRow() == 7);
    }
}
