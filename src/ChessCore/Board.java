package ChessCore;

public class Board {
    private final Piece[][] squares;

    public Board(){
        squares = new Piece[8][8];
        initializeBoard();
    }

    //test
    public Board(boolean initializePieces){
        squares = new Piece[8][8];
        if(initializePieces){
            Board standardBoard = new Board();

            for(int row = 0; row < 8; row++){
                for(int col = 0; col < 8; col++){
                    squares[row][col] = standardBoard.getPiece(new Position(row, col));
                }
            }
        }
    }

    private void initializeBoard(){

        setupBackRank(0, PieceColor.BLACK);

        for(int col = 0; col < 8; col++){
            squares[1][col] = new Pawn(PieceColor.BLACK);
        }

        setupBackRank(7, PieceColor.WHITE);

        for(int col = 0; col < 8; col++){
            squares[6][col] = new Pawn(PieceColor.WHITE);
        }
    }

    private void setupBackRank(int row, PieceColor color){

        squares[row][0] = new Rook(color);
        squares[row][1] = new Knight(color);
        squares[row][2] = new Bishop(color);
        squares[row][3] = new Queen(color);
        squares[row][4] = new King(color);
        squares[row][5] = new Bishop(color);
        squares[row][6] = new Knight(color);
        squares[row][7] = new Rook(color);
    }

    public Piece getPiece(Position position){
        return squares[position.getRow()][position.getCol()];
    }

    public void setPiece(Position position, Piece piece){
        squares[position.getRow()][position.getCol()] = piece;
    }

    public boolean isEmpty(Position position){
        return getPiece(position) == null;
    }
}
