package com.denzo.piece;

import com.denzo.Color;
import com.denzo.Coordinates;
import java.util.Set;

/**
 * Класс, представляющий фигуру слона.
 */
public class Bishop extends LongRangePiece implements IBishop {

    /**
     * Конструктор слона.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты слона на доске.
     */
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Возвращает множество возможных сдвигов координат для слона.
     * Слон может перемещаться по диагонали в любом направлении.
     *
     * @return множество сдвигов координат.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getBishopMoves();
    }
}
