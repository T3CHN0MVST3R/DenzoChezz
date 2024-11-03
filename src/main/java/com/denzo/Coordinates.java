package com.denzo;

import com.denzo.piece.CoordinatesShift;

/**
 * Класс, представляющий координаты на шахматной доске.
 */
public class Coordinates {
    /**
     * Файл (буква) на шахматной доске. Может принимать значения от A до H.
     */
    public final File file;

    /**
     * Ранг (число) на шахматной доске. Может принимать значения от 1 до 8.
     */
    public final Integer rank;

    /**
     * Конструктор координат.
     *
     * @param file  файл (буква) на шахматной доске.
     * @param rank  ранг (число) на шахматной доске.
     */
    public Coordinates(File file, Integer rank) {
        this.file = file;
        this.rank = rank;
    }

    /**
     * Возвращает новые координаты после применения сдвига.
     *
     * <p>
     * Метод создает новые координаты, добавляя значения сдвига к текущим координатам.
     * Предполагается, что перед вызовом этого метода проверяется возможность такого сдвига.
     * </p>
     *
     * @param shift объект {@link CoordinatesShift}, представляющий сдвиг по файлу и рангу.
     * @return новые координаты после применения сдвига.
     */
    public Coordinates shift(CoordinatesShift shift) {
        return new Coordinates(
                File.values()[this.file.ordinal() + shift.fileShift],
                this.rank + shift.rankShift
        );
    }

    /**
     * Проверяет, возможно ли выполнить сдвиг из текущих координат.
     *
     * <p>
     * Метод проверяет, не выходит ли результат сдвига за пределы шахматной доски.
     * </p>
     *
     * @param shift объект {@link CoordinatesShift}, представляющий сдвиг по файлу и рангу.
     * @return {@code true}, если сдвиг возможен без выхода за пределы доски, иначе {@code false}.
     */
    public boolean canShift(CoordinatesShift shift) {
        int f = file.ordinal() + shift.fileShift;
        int r = rank + shift.rankShift;

        // Проверка, что файл остается в диапазоне 0..7 (A..H)
        if ((f < 0) || (f > 7)) return false;

        // Проверка, что ранг остается в диапазоне 1..8
        if ((r < 1) || (r > 8)) return false;

        return true;
    }

    /**
     * Сравнивает текущий объект с другим объектом на равенство.
     *
     * <p>
     * Два объекта {@code Coordinates} считаются равными, если у них совпадают
     * и файл, и ранг.
     * </p>
     *
     * @param o объект для сравнения.
     * @return {@code true}, если объекты равны, иначе {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (file != that.file) return false;
        return rank.equals(that.rank);
    }

    /**
     * Возвращает хэш-код для текущего объекта.
     *
     * <p>
     * Хэш-код вычисляется на основе файла и ранга координат.
     * </p>
     *
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        int result = file.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }

    /**
     * Возвращает строковое представление координат.
     *
     * <p>
     * Формат представления: буква файла, за которой следует номер ранга (например, E2).
     * </p>
     *
     * @return строковое представление координат.
     */
    @Override
    public String toString() {
        return file + String.valueOf(rank);
    }
}
