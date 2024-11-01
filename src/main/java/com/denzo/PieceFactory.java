package com.denzo;

import com.denzo.piece.*;
import com.denzo.Color;
import com.denzo.Coordinates;

public class PieceFactory {

    public Piece fromFenChar(char fenChar, Coordinates coordinates) {
        switch (fenChar) {
            case 'p':
                return new Pawn(Color.BLACK, coordinates);
            case 'P':
                return new Pawn(Color.WHITE, coordinates);

            case 'r':
                return new Rook(Color.BLACK, coordinates);
            case 'R':
                return new Rook(Color.WHITE, coordinates);

            case 'n':
                return new Knight(Color.BLACK, coordinates);
            case 'N':
                return new Knight(Color.WHITE, coordinates);

            case 'b':
                return new Bishop(Color.BLACK, coordinates);
            case 'B':
                return new Bishop(Color.WHITE, coordinates);

            case 'q':
                return new Queen(Color.BLACK, coordinates);
            case 'Q':
                return new Queen(Color.WHITE, coordinates);

            case 'k':
                return new King(Color.BLACK, coordinates);
            case 'K':
                return new King(Color.WHITE, coordinates);

            default:
                throw new RuntimeException("Unknown FEN char!");
        }
    }

    public Piece createPiece(Class<? extends Piece> pieceClass, Color color, Coordinates coordinates) {
        try {
            return pieceClass.getConstructor(Color.class, Coordinates.class)
                    .newInstance(color, coordinates);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании фигуры: " + pieceClass.getSimpleName(), e);
        }
    }
}
