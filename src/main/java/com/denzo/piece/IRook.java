package com.denzo.piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Интерфейс, определяющий поведение ладьи (Rook) в шахматной игре
 */
public interface IRook {

    /**
     * Возвращает множество возможных сдвигов координат для ладьи.
     * Ладья может перемещаться по горизонтали и вертикали на любое количество клеток.
     *
     * <p>
     * Метод генерирует сдвиги координат для двух основных направлений:
     * <ul>
     *     <li>Слева направо и справа налево (left to right)</li>
     *     <li>Снизу вверх и сверху вниз (bottom to top)</li>
     * </ul>
     * </p>
     *
     * @return {@code Set<CoordinatesShift>} — множество сдвигов координат, доступных для ладьи.
     */
    default Set<CoordinatesShift> getRookMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        // Сдвиги по горизонтали слева направо и справа налево
        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue; // Игнорируем нулевой сдвиг (без перемещения)

            result.add(new CoordinatesShift(i, 0));
        }

        // Сдвиги по вертикали снизу вверх и сверху вниз
        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue; // Игнорируем нулевой сдвиг (без перемещения)

            result.add(new CoordinatesShift(0, i));
        }

        return result;
    }

}
