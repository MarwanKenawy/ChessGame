import ChessCore.*;

public class Main {
    public static void main(String[] args){

        // Test 1: King vs King
        Board board1 = new Board(false);

        board1.setPiece(new Position(7, 4), new King(PieceColor.WHITE));

        board1.setPiece(new Position(0, 4), new King(PieceColor.BLACK));

        ChessGame game1 = new ChessGame(board1);

        System.out.println("King vs King: " + game1.isInsufficientMaterial());

        // Test 2: King + Bishop vs King
        Board board2 = new Board(false);

        board2.setPiece(new Position(7, 4),new King(PieceColor.WHITE));

        board2.setPiece(new Position(0, 4), new King(PieceColor.BLACK));

        board2.setPiece(new Position(6, 2), new Bishop(PieceColor.WHITE));

        ChessGame game2 = new ChessGame(board2);

        System.out.println("King + Bishop vs King: " + game2.isInsufficientMaterial());

        // Test 3: King + Rook vs King
        Board board3 = new Board(false);

        board3.setPiece(new Position(7, 4), new King(PieceColor.WHITE));

        board3.setPiece(new Position(0, 4), new King(PieceColor.BLACK));

        board3.setPiece(new Position(6, 0), new Rook(PieceColor.WHITE));

        ChessGame game3 = new ChessGame(board3);

        System.out.println("King + Rook vs King: " + game3.isInsufficientMaterial());
    }
}
