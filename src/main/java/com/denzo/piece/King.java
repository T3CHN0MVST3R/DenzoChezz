package com.denzo.piece;

import com.denzo.board.Board;
import com.denzo.Color;
import com.denzo.Coordinates;
import com.denzo.File;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий фигуру короля.
 */
public class King extends Piece {

    /**
     * Конструктор короля.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты короля на доске.
     */
    public King(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Возвращает множество возможных сдвигов координат для короля.
     * Король может перемещаться на одну клетку в любом из восьми направлений.
     *
     * @return множество сдвигов координат.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        for (int fileShift = -1; fileShift <= 1; fileShift++) {
            for (int rankShift = -1; rankShift <= 1; rankShift++) {
                if ((fileShift == 0) && (rankShift == 0)) {
                    continue; // Король не может остаться на месте
                }

                result.add(new CoordinatesShift(fileShift, rankShift));
            }
        }

        return result;
    }

    /**
     * Проверяет, доступна ли указанная клетка для хода короля.
     * Король не может переместиться на клетку, которая находится под атакой противника.
     *
     * @param coordinates координаты целевой клетки.
     * @param board       текущая шахматная доска.
     * @return {@code true}, если клетка доступна для хода, иначе {@code false}.
     */
    @Override
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        boolean result = super.isSquareAvailableForMove(coordinates, board);

        if (result) {
            return !board.isSquareAttackedByColor(coordinates, color.opposite());
        }

        return false;
    }

    /**
     * Возвращает множество доступных клеток для хода короля, включая возможность рокировки.
     *
     * @param board текущая шахматная доска.
     * @return множество доступных клеток для хода.
     */
    @Override
    public Set<Coordinates> getAvailableMoveSquares(Board board) {
        Set<Coordinates> result = super.getAvailableMoveSquares(board);

        // Проверка возможности рокировки, если король ещё не двигался и не находится под шахом
        if (!hasMoved && !board.isSquareAttackedByColor(this.coordinates, color.opposite())) {
            // Проверка возможности короткой рокировки (рокировка вправо)
            if (canCastleKingSide(board)) {
                result.add(new Coordinates(File.G, this.coordinates.rank));
            }

            // Проверка возможности длинной рокировки (рокировка влево)
            if (canCastleQueenSide(board)) {
                result.add(new Coordinates(File.C, this.coordinates.rank));
            }
        }

        return result;
    }

    /**
     * Проверяет возможность короткой рокировки для короля.
     *
     * <p>
     * Условия для короткой рокировки:
     * <ul>
     *     <li>Ладья находится на правильной позиции (F-файл).</li>
     *     <li>Ладья не двигалась ранее.</li>
     *     <li>Клетки между королём и ладьёй пусты.</li>
     *     <li>Клетки, через которые проходит король, не находятся под атакой.</li>
     * </ul>
     * </p>
     *
     * @param board текущая шахматная доска.
     * @return {@code true}, если короткая рокировка возможна, иначе {@code false}.
     */
    private boolean canCastleKingSide(Board board) {
        // Координаты ладьи на короткой стороне
        Coordinates rookCoordinates = new Coordinates(File.H, this.coordinates.rank);
        Piece rook = board.getPiece(rookCoordinates);

        // Проверка наличия ладьи, её состояния и соответствия цвета
        if (rook == null || rook.hasMoved || !(rook instanceof Rook) || rook.color != this.color) {
            return false;
        }

        // Проверка, что клетки между королём и ладьёй пусты
        Coordinates fSquare = new Coordinates(File.F, this.coordinates.rank);
        Coordinates gSquare = new Coordinates(File.G, this.coordinates.rank);
        if (!board.isSquareEmpty(fSquare) || !board.isSquareEmpty(gSquare)) {
            return false;
        }

        // Проверка, что клетки не находятся под атакой
        if (board.isSquareAttackedByColor(fSquare, color.opposite()) ||
                board.isSquareAttackedByColor(gSquare, color.opposite())) {
            return false;
        }

        return true;
    }

    /**
     * Проверяет возможность длинной рокировки для короля.
     *
     * <p>
     * Условия для длинной рокировки:
     * <ul>
     *     <li>Ладья находится на правильной позиции (A-файл).</li>
     *     <li>Ладья не двигалась ранее.</li>
     *     <li>Клетки между королём и ладьёй пусты.</li>
     *     <li>Клетки, через которые проходит король, не находятся под атакой.</li>
     * </ul>
     * </p>
     *
     * @param board текущая шахматная доска.
     * @return {@code true}, если длинная рокировка возможна, иначе {@code false}.
     */
    private boolean canCastleQueenSide(Board board) {
        // Координаты ладьи на длинной стороне
        Coordinates rookCoordinates = new Coordinates(File.A, this.coordinates.rank);
        Piece rook = board.getPiece(rookCoordinates);

        // Проверка наличия ладьи, её состояния и соответствия цвета
        if (rook == null || rook.hasMoved || !(rook instanceof Rook) || rook.color != this.color) {
            return false;
        }

        // Проверка, что клетки между королём и ладьёй пусты
        Coordinates bSquare = new Coordinates(File.B, this.coordinates.rank);
        Coordinates cSquare = new Coordinates(File.C, this.coordinates.rank);
        Coordinates dSquare = new Coordinates(File.D, this.coordinates.rank);
        if (!board.isSquareEmpty(bSquare) || !board.isSquareEmpty(cSquare) || !board.isSquareEmpty(dSquare)) {
            return false;
        }

        // Проверка, что клетки не находятся под атакой
        if (board.isSquareAttackedByColor(dSquare, color.opposite()) ||
                board.isSquareAttackedByColor(cSquare, color.opposite())) {
            return false;
        }

        return true;
    }
}
