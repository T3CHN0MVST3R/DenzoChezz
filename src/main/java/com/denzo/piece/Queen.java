package com.denzo.piece;

import com.denzo.Color;
import com.denzo.Coordinates;

import java.util.Set;

/**
 * Класс, представляющий фигуру ферзя.
 */
public class Queen extends LongRangePiece implements IBishop, IRook {

    /**
     * Конструктор ферзя.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты ферзя на доске.
     */
    public Queen(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Возвращает множество возможных сдвигов координат для ферзя.
     *
     * <p>
     * Ферзь комбинирует ходы слона и ладьи, позволяя ему перемещаться
     * по диагонали, горизонтали и вертикали на любое количество клеток.
     * </p>
     *
     * @return {@code Set<CoordinatesShift>} — множество сдвигов координат, доступных для ферзя.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        // Получение сдвигов как для слона, так и для ладьи
        Set<CoordinatesShift> moves = getBishopMoves();
        moves.addAll(getRookMoves());

        return moves;
    }
}
