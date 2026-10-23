package ChessCore.board;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col){
        if(row < 0 || row >= 8 || col < 0 || col >= 8){
            throw new IllegalArgumentException("Invalid Position");
        }

        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public static Position fromChessNotation(String notation){
        if(notation == null || notation.length() != 2){
            throw new IllegalArgumentException("Invalid position");
        }

        notation = notation.toUpperCase();

        char file = notation.charAt(0);
        char rank = notation.charAt(1);

        if(file < 'A' || file > 'H' || rank < '1' || rank > '8'){
            throw new IllegalArgumentException("Invalid position");
        }

        int col = file - 'A';
        int row = 8 - (rank - '0');

        return new Position(row, col);
    }
}
