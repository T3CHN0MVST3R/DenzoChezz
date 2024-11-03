package com.denzo.piece;

import com.denzo.board.Board;
import com.denzo.board.Move;
import com.denzo.Color;
import com.denzo.Coordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий фигуру пешки.
 */
public class Pawn extends Piece {

    /**
     * Конструктор пешки.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты пешки на доске.
     */
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Возвращает множество возможных сдвигов координат для пешки.
     * Пешка может перемещаться вперед на одну клетку, дважды вперед с начальной позиции,
     * а также атаковать по диагонали на одну клетку вперед.
     *
     * @return {@code Set<CoordinatesShift>} — множество сдвигов координат, доступных для пешки.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        int direction = color == Color.WHITE ? 1 : -1;

        // Ход вперед на одну клетку
        result.add(new CoordinatesShift(0, direction));

        // Начальный двойной ход на две клетки вперед
        if ((color == Color.WHITE && coordinates.rank == 2) || (color == Color.BLACK && coordinates.rank == 7)) {
            result.add(new CoordinatesShift(0, 2 * direction));
        }

        // Взятия по диагонали на одну клетку вперед
        result.add(new CoordinatesShift(-1, direction));
        result.add(new CoordinatesShift(1, direction));

        return result;
    }

    /**
     * Возвращает множество возможных атакующих сдвигов координат для пешки.
     * Пешка может атаковать по диагонали на одну клетку вперед.
     *
     * @return {@code Set<CoordinatesShift>} — множество атакующих сдвигов координат для пешки.
     */
    @Override
    protected Set<CoordinatesShift> getPieceAttacks() {
        Set<CoordinatesShift> result = new HashSet<>();

        int direction = color == Color.WHITE ? 1 : -1;

        // Атакующие сдвиги по диагонали
        result.add(new CoordinatesShift(-1, direction));
        result.add(new CoordinatesShift(1, direction));

        return result;
    }

    /**
     * Проверяет, доступна ли указанная клетка для хода пешки.
     *
     * <p>
     * Пешка может двигаться вперед на одну клетку, если она пуста.
     * С начальной позиции пешка может сделать двойной ход на две клетки вперед,
     * если обе клетки на пути свободны. Также пешка может атаковать по диагонали
     * на одну клетку вперед, если там находится фигура противника или выполняется взятие En Passant.
     * </p>
     *
     * @param target координаты целевой клетки для хода.
     * @param board  текущая шахматная доска.
     * @return {@code true}, если клетка доступна для хода, иначе {@code false}.
     */
    @Override
    protected boolean isSquareAvailableForMove(Coordinates target, Board board) {
        int rankDifference = target.rank - this.coordinates.rank;
        int direction = color == Color.WHITE ? 1 : -1;

        if (this.coordinates.file == target.file) {
            // Ход вперед на одну клетку
            if (rankDifference == direction) {
                return board.isSquareEmpty(target);
            }
            // Двойной ход с начальной позиции
            else if (rankDifference == 2 * direction) {
                if ((color == Color.WHITE && coordinates.rank == 2) || (color == Color.BLACK && coordinates.rank == 7)) {
                    Coordinates between = new Coordinates(coordinates.file, coordinates.rank + direction);
                    return board.isSquareEmpty(between) && board.isSquareEmpty(target);
                }
            }
        } else {
            // Взятие фигуры или En Passant по диагонали
            if (Math.abs(this.coordinates.file.ordinal() - target.file.ordinal()) == 1 && rankDifference == direction) {
                // Обычное взятие фигуры противника
                if (!board.isSquareEmpty(target) && board.getPiece(target).color != color) {
                    return true;
                }

                // Взятие En Passant
                Move lastMove = board.getLastMove();
                if (lastMove != null && lastMove.pieceMoved instanceof Pawn
                        && Math.abs(lastMove.from.rank - lastMove.to.rank) == 2
                        && lastMove.to.rank == this.coordinates.rank
                        && lastMove.to.file == target.file) {
                    return true;
                }
            }
        }

        return false;
    }
}
