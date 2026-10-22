package ChessCore;

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
}
