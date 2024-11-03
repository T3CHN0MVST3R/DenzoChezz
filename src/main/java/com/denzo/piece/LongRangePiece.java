package com.denzo.piece;

import com.denzo.board.Board;
import com.denzo.board.BoardUtils;
import com.denzo.Color;
import com.denzo.Coordinates;

import java.util.List;

/**
 * Абстрактный класс, представляющий фигуры, способные двигаться на большие расстояния
 * по прямым линиям (вертикально, горизонтально или по диагонали) на шахматной доске.
 */
public abstract class LongRangePiece extends Piece {

    /**
     * Конструктор длинноходящей фигуры.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты фигуры на доске.
     */
    public LongRangePiece(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Проверяет, доступна ли указанная клетка для хода фигуры.
     *
     * <p>
     * Этот метод сначала вызывает метод родительского класса для базовой проверки,
     * затем дополнительно проверяет, нет ли препятствий на пути к целевой клетке.
     * </p>
     *
     * @param coordinates координаты целевой клетки.
     * @param board       текущая шахматная доска.
     * @return {@code true}, если клетка доступна для хода, иначе {@code false}.
     */
    @Override
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        boolean result = super.isSquareAvailableForMove(coordinates, board);

        if (result) {
            return isSquareAvailableForAttack(coordinates, board);
        } else {
            return false;
        }
    }

    /**
     * Проверяет, доступна ли указанная клетка для атаки фигуры.
     *
     * <p>
     * Для длинноходящих фигур проверяется, нет ли других фигур на пути к целевой клетке.
     * Метод определяет направление движения (вертикальное, горизонтальное или диагональное)
     * и использует соответствующий метод из {@link BoardUtils} для получения списка клеток между
     * исходной и целевой позицией. Если любая из промежуточных клеток занята, ход считается недоступным.
     * </p>
     *
     * @param coordinates координаты целевой клетки.
     * @param board       текущая шахматная доска.
     * @return {@code true}, если клетка доступна для атаки, иначе {@code false}.
     */
    @Override
    protected boolean isSquareAvailableForAttack(Coordinates coordinates, Board board) {
        List<Coordinates> coordinatesBetween;

        // Определение направления движения и получение промежуточных клеток
        if (this.coordinates.file == coordinates.file) {
            // Движение по вертикали (одна и та же колонка)
            coordinatesBetween = BoardUtils.getVerticalCoordinatesBetween(this.coordinates, coordinates);
        } else if (this.coordinates.rank.equals(coordinates.rank)) {
            // Движение по горизонтали (один и тот же ряд)
            coordinatesBetween = BoardUtils.getHorizontalCoordinatesBetween(this.coordinates, coordinates);
        } else {
            // Движение по диагонали
            coordinatesBetween = BoardUtils.getDiagonalCoordinatesBetween(this.coordinates, coordinates);
        }

        // Проверка, что все промежуточные клетки пусты
        for (Coordinates c : coordinatesBetween) {
            if (!board.isSquareEmpty(c)) {
                return false; // На пути есть фигура, ход невозможен
            }
        }

        return true; // Все клетки на пути пусты, ход возможен
    }
}
