package com.denzo;

import com.denzo.board.Board;
import com.denzo.piece.Piece;

import java.util.List;
import java.util.Set;

/**
 * Класс для проверки состояния игры на патовую ситуацию (stalemate).
 *
 * <p>
 * Этот класс наследуется от {@link GameStateChecker} и реализует метод {@code check},
 * который определяет, находится ли игра в состоянии пата для указанного игрока.
 * Состояние пата наступает, когда у игрока нет доступных ходов, но его король не находится
 * под шахом.
 * </p>
 *
 * <p>
 * Класс используется в классе {@link Game} для определения, завершилась ли игра патовой ситуацией.
 */
public class StalemateGameStateChecker extends GameStateChecker {

    /**
     * Проверяет, находится ли игра в состоянии пата для указанного игрока.
     *
     * <p>
     * Метод выполняет следующие шаги:
     * <ol>
     *     <li>Получает список всех фигур указанного цвета.</li>
     *     <li>Перебирает все доступные ходы для каждой фигуры.</li>
     *     <li>Если найдется хотя бы один доступный ход, возвращает {@link GameState#ONGOING}.</li>
     *     <li>Если ни одного хода не найдено, возвращает {@link GameState#STALEMATE}.</li>
     * </ol>
     * </p>
     *
     * <p>
     * Важно отметить, что данный метод предполагает, что проверка на шах уже выполнена
     * и король не находится под атакой. Если король под атакой и нет доступных ходов,
     * это уже должно быть обработано проверяющим {@link CheckmateGameStateChecker}.
     * </p>
     *
     * @param board текущая шахматная доска.
     * @param color цвет игрока (WHITE или BLACK), для которого проверяется состояние игры.
     * @return {@link GameState#ONGOING}, если есть доступные ходы, иначе {@link GameState#STALEMATE}.
     */
    @Override
    public GameState check(Board board, Color color) {
        // Получение списка всех фигур указанного цвета на доске
        List<Piece> pieces = board.getPiecesByColor(color);

        // Перебор всех фигур для проверки наличия доступных ходов
        for (Piece piece : pieces) {
            // Получение всех доступных клеток для хода текущей фигуры
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            // Если найдется хотя бы один доступный ход, игра продолжается
            if (availableMoveSquares.size() > 0) {
                return GameState.ONGOING;
            }
        }

        // Если ни одной доступной клетки для хода не найдено, объявляется пат
        return GameState.STALEMATE;
    }
}
