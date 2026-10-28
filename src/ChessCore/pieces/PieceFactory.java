package ChessCore.pieces;

import ChessCore.enums.PieceColor;

public class PieceFactory {
    public static Piece createPromotionPiece(char choice, PieceColor color){
        switch (Character.toUpperCase(choice)) {
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
}
