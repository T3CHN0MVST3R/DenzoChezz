package com.denzo.board;

import com.denzo.Coordinates;
import com.denzo.piece.Piece;

public class Move {
    public final Coordinates from, to;
    public boolean isCastlingMove = false; // Новый флаг
    public Coordinates rookFrom, rookTo;    // Позиции ладьи при рокировке
    public Class<? extends Piece> promotionPieceType = null;

    public Move(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }
}
