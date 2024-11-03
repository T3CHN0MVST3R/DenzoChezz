package com.denzo.piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Интерфейс, определяющий поведение слона.
 */
public interface IBishop {

    /**
     * Возвращает множество возможных сдвигов координат для слона.
     * Слон может перемещаться по диагонали в любом направлении на любое количество клеток.
     *
     * <p>
     * Метод генерирует сдвиги координат для двух основных направлений:
     * <ul>
     *     <li>Слева-вниз (bottom-left) до справа-вверх (top-right)</li>
     *     <li>Слева-вверх (top-left) до справа-вниз (bottom-right)</li>
     * </ul>
     * </p>
     *
     * @return {@code Set<CoordinatesShift>} — множество сдвигов координат, доступных для слона.
     */
    default Set<CoordinatesShift> getBishopMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        // Сдвиги по диагонали слева-вниз до справа-вверх
        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue; // Игнорируем нулевой сдвиг (без перемещения)

            result.add(new CoordinatesShift(i, i));
        }

        // Сдвиги по диагонали слева-вверх до справа-вниз
        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue; // Игнорируем нулевой сдвиг (без перемещения)

            result.add(new CoordinatesShift(i, -i));
        }

        return result;
    }

}
