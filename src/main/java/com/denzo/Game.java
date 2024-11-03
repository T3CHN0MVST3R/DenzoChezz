package com.denzo;

import com.denzo.board.Board;
import com.denzo.board.BoardFactory;
import com.denzo.board.Move;

import java.util.List;

/**
 * Класс, представляющий основную логику шахматной игры.
 *
 * <p>
 * Этот класс управляет игровым циклом, обрабатывает ходы игроков,
 * проверяет состояние игры (например, шах, мат, пат) и отображает
 * текущее состояние доски.
 * </p>
 */
public class Game {
    /**
     * Текущая шахматная доска.
     */
    private final Board board;

    /**
     * Рендерер для отображения состояния доски в консоли.
     */
    private final BoardConsoleRenderer renderer = new BoardConsoleRenderer();

    /**
     * Список проверяющих состояния игры.
     *
     * <p>
     * Включает в себя проверку на пат и мат с помощью соответствующих классов.
     * </p>
     */
    private final List<GameStateChecker> checkers = List.of(
            new StalemateGameStateChecker(),
            new CheckmateGameStateChecker()
    );

    /**
     * Конструктор игры.
     *
     * @param board начальная шахматная доска.
     */
    public Game(Board board) {
        this.board = board;
    }

    /**
     * Основной игровой цикл.
     *
     * <p>
     * Цикл продолжается до тех пор, пока состояние игры не станет
     * {@link GameState#ONGOING}. На каждом шаге цикла происходит:
     * </p>
     * <ol>
     *     <li>Отображение текущего состояния доски.</li>
     *     <li>Вывод сообщения о текущем игроке, который должен сделать ход.</li>
     *     <li>Получение хода от пользователя.</li>
     *     <li>Выполнение хода на доске.</li>
     *     <li>Смена текущего игрока.</li>
     *     <li>Проверка состояния игры после хода.</li>
     * </ol>
     *
     * <p>
     * После завершения цикла происходит окончательное отображение доски
     * и вывод итогового состояния игры.
     * </p>
     */
    public void gameLoop() {
        // Начальный игрок - белые
        Color colorToMove = Color.WHITE;

        // Определение начального состояния игры
        GameState state = determineGameState(board, colorToMove);

        // Цикл игры продолжается, пока состояние игры - продолжается
        while (state == GameState.ONGOING) {
            // Отображение текущего состояния доски
            renderer.render(board);

            // Вывод сообщения о текущем игроке
            if (colorToMove == Color.WHITE) {
                System.out.println("White to move");
            } else {
                System.out.println("Black to move");
            }

            // Получение хода от пользователя через ввод координат
            Move move = InputCoordinates.inputMove(board, colorToMove, renderer);

            // Выполнение хода на доске
            board.makeMove(move);

            // Смена текущего игрока
            colorToMove = colorToMove.opposite();

            // Определение нового состояния игры после хода
            state = determineGameState(board, colorToMove);
        }

        // Окончательное отображение доски
        renderer.render(board);
        // Вывод итогового состояния игры
        System.out.println("Game ended with state = " + state);
    }

    /**
     * Определяет текущее состояние игры для указанного игрока.
     *
     * <p>
     * Метод перебирает все проверяющие состояния игры и возвращает первое
     * обнаруженное состояние, отличное от {@link GameState#ONGOING}.
     * Если ни один из проверяющих не определил состояние игры, возвращается
     * {@link GameState#ONGOING}.
     * </p>
     *
     * @param board текущая шахматная доска.
     * @param color цвет игрока, для которого проверяется состояние игры (WHITE или BLACK).
     * @return {@link GameState} — текущее состояние игры.
     */
    private GameState determineGameState(Board board, Color color) {
        for (GameStateChecker checker : checkers) {
            GameState state = checker.check(board, color);

            if (state != GameState.ONGOING) {
                return state;
            }
        }

        return GameState.ONGOING;
    }
}
