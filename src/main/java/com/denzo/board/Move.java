package com.denzo.board;

import com.denzo.Coordinates;
import com.denzo.piece.Piece;

public class Move {
    public final Coordinates from, to;
    public boolean isCastlingMove = false;
    public Coordinates rookFrom, rookTo;
    public boolean isEnPassantMove = false; // Новое поле для En Passant
    public Class<? extends Piece> promotionPieceType = null; // Поле для превращения
    public Piece pieceMoved; // Новое поле для хранения перемещённой фигуры

    public Move(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }
}
