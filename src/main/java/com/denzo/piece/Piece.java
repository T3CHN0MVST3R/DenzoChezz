package com.denzo.piece;

import com.denzo.board.Board;
import com.denzo.Color;
import com.denzo.Coordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * Абстрактный класс, представляющий шахматную фигуру.
 *
 * <p>
 * Этот класс служит базой для всех конкретных типов фигур в шахматной игре,
 * таких как пешка, конь, слон, ладья, ферзь и король. Он содержит общие
 * свойства и методы, которые наследуются и могут быть переопределены в
 * подклассах для реализации специфического поведения каждой фигуры.
 * </p>
 */
abstract public class Piece {

    /**
     * Цвет фигуры (WHITE или BLACK).
     */
    public final Color color;

    /**
     * Текущие координаты фигуры на шахматной доске.
     */
    public Coordinates coordinates;

    /**
     * Флаг, указывающий, была ли фигура уже перемещена.
     * Используется для определения возможности выполнения специальных ходов,
     * таких как рокировка или двойной ход пешкой с начальной позиции.
     */
    public boolean hasMoved = false;

    /**
     * Конструктор фигуры.
     *
     * @param color       Цвет фигуры (WHITE или BLACK).
     * @param coordinates Начальные координаты фигуры на доске.
     */
    public Piece(Color color, Coordinates coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }

    /**
     * Возвращает множество доступных клеток для хода фигуры на основе
     * текущего состояния доски.
     *
     * <p>
     * Метод перебирает все возможные сдвиги координат, определенные
     * в {@link #getPieceMoves()}, и проверяет, можно ли выполнить ход
     * в каждую из этих клеток. Если клетка доступна для хода, она добавляется
     * в результат.
     * </p>
     *
     * @param board Текущая шахматная доска.
     * @return {@code Set<Coordinates>} — множество координат, на которые можно сделать ход.
     */
    public Set<Coordinates> getAvailableMoveSquares(Board board) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : getPieceMoves()) {
            if (coordinates.canShift(shift)) {
                Coordinates newCoordinates = coordinates.shift(shift);

                if (isSquareAvailableForMove(newCoordinates, board)) {
                    result.add(newCoordinates);
                }
            }
        }

        return result;
    }

    /**
     * Проверяет, доступна ли указанная клетка для хода фигуры.
     *
     * <p>
     * Клетка считается доступной для хода, если она пуста или занята фигурой
     * противоположного цвета.
     * </p>
     *
     * @param coordinates Координаты целевой клетки.
     * @param board       Текущая шахматная доска.
     * @return {@code true}, если клетка доступна для хода, иначе {@code false}.
     */
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        return board.isSquareEmpty(coordinates) || board.getPiece(coordinates).color != color;
    }

    /**
     * Абстрактный метод для получения множества возможных сдвигов координат
     * для конкретной фигуры.
     *
     * <p>
     * Каждый подкласс должен реализовать этот метод, возвращая сдвиги,
     * характерные для своей фигуры.
     * </p>
     *
     * @return {@code Set<CoordinatesShift>} — множество возможных сдвигов координат.
     */
    protected abstract Set<CoordinatesShift> getPieceMoves();

    /**
     * Возвращает множество атакующих сдвигов координат для фигуры.
     *
     * <p>
     * По умолчанию этот метод возвращает то же множество, что и {@link #getPieceMoves()},
     * но может быть переопределен в подклассах для предоставления специфических
     * атакующих сдвигов.
     * </p>
     *
     * @return {@code Set<CoordinatesShift>} — множество атакующих сдвигов координат.
     */
    protected Set<CoordinatesShift> getPieceAttacks() {
        return getPieceMoves();
    }

    /**
     * Возвращает множество клеток, которые атакует фигура на основе текущего состояния доски.
     *
     * <p>
     * Метод перебирает все атакующие сдвиги координат, определенные в {@link #getPieceAttacks()},
     * и проверяет, можно ли выполнить атаку в каждую из этих клеток.
     * Если клетка доступна для атаки, она добавляется в результат.
     * </p>
     *
     * @param board Текущая шахматная доска.
     * @return {@code Set<Coordinates>} — множество координат, которые атакует фигура.
     */
    public Set<Coordinates> getAttackedSquares(Board board) {
        Set<CoordinatesShift> pieceAttacks = getPieceAttacks();
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift pieceAttack : pieceAttacks) {
            if (coordinates.canShift(pieceAttack)) {
                Coordinates shiftedCoordinates = coordinates.shift(pieceAttack);

                if (isSquareAvailableForAttack(shiftedCoordinates, board)) {
                    result.add(shiftedCoordinates);
                }
            }
        }

        return result;
    }

    /**
     * Проверяет, доступна ли указанная клетка для атаки фигуры.
     *
     * <p>
     * По умолчанию клетка считается доступной для атаки. Подклассы могут переопределить этот метод
     * для реализации специфической логики атаки.
     * </p>
     *
     * @param coordinates Координаты целевой клетки для атаки.
     * @param board       Текущая шахматная доска.
     * @return {@code true}, если клетка доступна для атаки, иначе {@code false}.
     */
    protected boolean isSquareAvailableForAttack(Coordinates coordinates, Board board) {
        return true;
    }
}
