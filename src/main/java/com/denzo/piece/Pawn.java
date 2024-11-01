package com.denzo.piece;

import com.denzo.board.Board;
import com.denzo.Color;
import com.denzo.Coordinates;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        int direction = color == Color.WHITE ? 1 : -1;

        // Ход вперед
        result.add(new CoordinatesShift(0, direction));

        // Начальный двойной ход
        if ((color == Color.WHITE && coordinates.rank == 2) || (color == Color.BLACK && coordinates.rank == 7)) {
            result.add(new CoordinatesShift(0, 2 * direction));
        }

        // Взятия по диагонали
        result.add(new CoordinatesShift(-1, direction));
        result.add(new CoordinatesShift(1, direction));

        return result;
    }

    @Override
    protected Set<CoordinatesShift> getPieceAttacks() {
        Set<CoordinatesShift> result = new HashSet<>();

        int direction = color == Color.WHITE ? 1 : -1;

        result.add(new CoordinatesShift(-1, direction));
        result.add(new CoordinatesShift(1, direction));

        return result;
    }

    @Override
    protected boolean isSquareAvailableForMove(Coordinates target, Board board) {
        int rankDifference = target.rank - this.coordinates.rank;
        int direction = color == Color.WHITE ? 1 : -1;

        if (this.coordinates.file == target.file) {
            // Ход вперед
            if (rankDifference == direction) {
                return board.isSquareEmpty(target);
            } else if (rankDifference == 2 * direction) {
                // Двойной ход с начальной позиции
                if ((color == Color.WHITE && coordinates.rank == 2) || (color == Color.BLACK && coordinates.rank == 7)) {
                    Coordinates between = new Coordinates(coordinates.file, coordinates.rank + direction);
                    return board.isSquareEmpty(between) && board.isSquareEmpty(target);
                }
            }
        } else {
            // Взятие фигуры
            if (Math.abs(this.coordinates.file.ordinal() - target.file.ordinal()) == 1 && rankDifference == direction) {
                return !board.isSquareEmpty(target) && board.getPiece(target).color != color;
            }
        }

        return false;
    }
}
