package com.denzo.piece;

import com.denzo.Color;
import com.denzo.Coordinates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий фигуру коня.
 */
public class Knight extends Piece {

    /**
     * Конструктор коня.
     *
     * @param color       цвет фигуры (WHITE или BLACK).
     * @param coordinates начальные координаты коня на доске.
     */
    public Knight(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Возвращает множество возможных сдвигов координат для коня.
     * Конь может перемещаться в восьми различных направлениях, соответствующих
     * движению в форме буквы "Г".
     *
     * @return {@code Set<CoordinatesShift>} — множество сдвигов координат, доступных для коня.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return new HashSet<>(Arrays.asList(
                new CoordinatesShift(1, 2),   // Движение на одну клетку вправо и две вверх
                new CoordinatesShift(2, 1),   // Движение на две клетки вправо и одну вверх

                new CoordinatesShift(2, -1),  // Движение на две клетки вправо и одну вниз
                new CoordinatesShift(1, -2),  // Движение на одну клетку вправо и две вниз

                new CoordinatesShift(-2, -1), // Движение на две клетки влево и одну вниз
                new CoordinatesShift(-1, -2), // Движение на одну клетку влево и две вниз

                new CoordinatesShift(-2, 1),  // Движение на две клетки влево и одну вверх
                new CoordinatesShift(-1, 2)   // Движение на одну клетку влево и две вверх
        ));
    }
}
