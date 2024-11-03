package com.denzo.board;

import com.denzo.Coordinates;
import com.denzo.File;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с координатами на шахматной доске.
 * Предоставляет методы для получения списка координат между двумя точками
 * по диагонали, вертикали и горизонтали.
 */
public class BoardUtils {

    /**
     * Возвращает список координат между двумя точками по диагонали.
     *
     * <p>
     * Допущение: клетки лежат на одной диагонали.
     * </p>
     *
     * @param source исходные координаты.
     * @param target целевые координаты.
     * @return список координат между {@code source} и {@code target}.
     */
    public static List<Coordinates> getDiagonalCoordinatesBetween(Coordinates source, Coordinates target) {
        // допущение - клетки лежат на одной диагонали

        List<Coordinates> result = new ArrayList<>();

        int fileShift = source.file.ordinal() < target.file.ordinal() ? 1 : -1;
        int rankShift = source.rank < target.rank ? 1 : -1;

        for (
                int fileIndex = source.file.ordinal() + fileShift,
                rank = source.rank + rankShift;

                fileIndex != target.file.ordinal() && rank != target.rank;

                fileIndex += fileShift, rank += rankShift
        ) {
            result.add(new Coordinates(File.values()[fileIndex], rank));
        }

        return result;
    }

    /**
     * Возвращает список координат между двумя точками по вертикали.
     *
     * <p>
     * Допущение: клетки лежат на одной вертикали.
     * </p>
     *
     * @param source исходные координаты.
     * @param target целевые координаты.
     * @return список координат между {@code source} и {@code target}.
     */
    public static List<Coordinates> getVerticalCoordinatesBetween(Coordinates source, Coordinates target) {
        // допущение - клетки лежат на одной вертикали

        List<Coordinates> result = new ArrayList<>();

        int rankShift = source.rank < target.rank ? 1 : -1;

        for (int rank = source.rank + rankShift; rank != target.rank; rank += rankShift) {
            result.add(new Coordinates(source.file, rank));
        }

        return result;
    }

    /**
     * Возвращает список координат между двумя точками по горизонтали.
     *
     * <p>
     * Допущение: клетки лежат на одной горизонтали.
     * </p>
     *
     * @param source исходные координаты.
     * @param target целевые координаты.
     * @return список координат между {@code source} и {@code target}.
     */
    public static List<Coordinates> getHorizontalCoordinatesBetween(Coordinates source, Coordinates target) {
        // допущение - клетки лежат на одной горизонтали

        List<Coordinates> result = new ArrayList<>();

        int fileShift = source.file.ordinal() < target.file.ordinal() ? 1 : -1;

        for (
                int fileIndex = source.file.ordinal() + fileShift; fileIndex != target.file.ordinal();
                fileIndex += fileShift
        ) {
            result.add(new Coordinates(File.values()[fileIndex], source.rank));
        }

        return result;
    }

    /**
     * Основной метод для тестирования утилитных методов.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        // Пример использования метода getHorizontalCoordinatesBetween
        List<Coordinates> list = getHorizontalCoordinatesBetween(new Coordinates(File.D, 4), new Coordinates(File.H, 4));
        System.out.println("list = " + list);
    }
}
