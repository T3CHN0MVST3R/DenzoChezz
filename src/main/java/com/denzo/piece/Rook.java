package com.denzo.piece;

import com.denzo.Color;
import com.denzo.Coordinates;
import java.util.Set;

/**
 * Класс, представляющий фигуру ладьи (Rook) в шахматной игре.
 *
 * <p>
 * Ладья может перемещаться по горизонтали и вертикали на любое количество клеток.
 * Этот класс наследуется от {@link LongRangePiece} и реализует интерфейс {@link IRook},
 * объединяя их функциональность для обеспечения всех возможных ходов ладьи.
 * </p>
 */
public class Rook extends LongRangePiece implements IRook {

    /**
     * Конструктор ладьи.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты ладьи на доске.
     */
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Возвращает множество возможных сдвигов координат для ладьи.
     *
     * <p>
     * Ладья может перемещаться только по горизонтали и вертикали. Этот метод
     * объединяет сдвиги, полученные из интерфейса {@link IRook}, для
     * определения всех доступных ходов.
     * </p>
     *
     * @return {@code Set<CoordinatesShift>} — множество сдвигов координат, доступных для ладьи.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getRookMoves();
    }
}
