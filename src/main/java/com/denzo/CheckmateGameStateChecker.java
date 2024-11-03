package com.denzo;

import com.denzo.board.Board;
import com.denzo.board.BoardFactory;
import com.denzo.board.Move;
import com.denzo.piece.King;
import com.denzo.piece.Piece;

import java.util.List;
import java.util.Set;

/**
 * Класс для проверки состояния игры на наличие матовой ситуации.
 *
 * <p>
 * Этот класс определяет логику проверки, находится ли король игрока
 * в состоянии шаха и мат. Если король находится в шахе и нет ни одного
 * допустимого хода, который бы вывел его из шаха, игра завершается матом.
 * </p>
 *
 * <p>
 * Класс наследуется от {@link GameStateChecker} и реализует метод {@code check},
 * который возвращает текущее состояние игры.
 * </p>
 */
public class CheckmateGameStateChecker extends GameStateChecker {

    /**
     * Проверяет состояние игры для указанного игрока на предмет мата.
     *
     * <p>
     * Метод выполняет следующие шаги:
     * <ol>
     *     <li>Находит короля текущего игрока.</li>
     *     <li>Проверяет, находится ли король в шахе.</li>
     *     <li>Если король не в шахе, игра продолжается.</li>
     *     <li>Если король в шахе, проверяет все возможные ходы всех фигур текущего игрока.</li>
     *     <li>Для каждого хода выполняет следующие действия:
     *         <ul>
     *             <li>Создает копию текущей доски.</li>
     *             <li>Выполняет ход на копии доски.</li>
     *             <li>Проверяет, остался ли король под атакой после хода.</li>
     *             <li>Если найдется хотя бы один ход, выводящий короля из шаха, игра продолжается.</li>
     *         </ul>
     *     </li>
     *     <li>Если не найдено ни одного хода, выводящего короля из шаха, объявляется мат.</li>
     * </ol>
     * </p>
     *
     * @param board текущая шахматная доска.
     * @param color цвет игрока, для которого проверяется состояние игры (WHITE или BLACK).
     * @return {@link GameState} — текущее состояние игры (ONGOING, CHECKMATE_TO_WHITE_KING, CHECKMATE_TO_BLACK_KING).
     */
    @Override
    public GameState check(Board board, Color color) {
        // Поиск короля текущего игрока на доске
        Piece king = board.getPiecesByColor(color).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .get(); // Предполагается, что король всегда присутствует

        // Проверка, находится ли король в шахе
        if (!board.isSquareAttackedByColor(king.coordinates, color.opposite())) {
            return GameState.ONGOING; // Король не в шахе, игра продолжается
        }

        // Получение всех фигур текущего игрока
        List<Piece> pieces = board.getPiecesByColor(color);

        // Перебор всех фигур для поиска допустимого хода, который бы вывел короля из шаха
        for (Piece piece : pieces) {
            // Получение всех доступных клеток для хода данной фигуры
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            for (Coordinates coordinates : availableMoveSquares) {
                // Создание копии текущей доски
                Board clone = new BoardFactory().copy(board);

                // Выполнение хода на копии доски
                clone.makeMove(new Move(piece.coordinates, coordinates));

                // Поиск короля на копии доски после хода
                Piece clonedKing = clone.getPiecesByColor(color).stream()
                        .filter(p -> p instanceof King)
                        .findFirst()
                        .get();

                // Проверка, не находится ли король под атакой после хода
                if (!clone.isSquareAttackedByColor(clonedKing.coordinates, color.opposite())) {
                    return GameState.ONGOING; // Найден ход, выводящий короля из шаха
                }
            }
        }

        // Если нет доступных ходов, выводится мат
        if (color == Color.WHITE) {
            return GameState.CHECKMATE_TO_WHITE_KING;
        } else {
            return GameState.CHECKMATE_TO_BLACK_KING;
        }
    }
}
