package com.denzo;

import com.denzo.board.Board;
import com.denzo.piece.Piece;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * Класс для рендеринга шахматной доски в консоли.
 */
public class BoardConsoleRenderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
    public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";

    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";

    public static final String ANSI_HIGHLIGHTED_SQUARE_BACKGROUND = "\u001B[45m";

    /**
     * Рендерит шахматную доску, подчеркивая возможные ходы выбранной фигуры.
     *
     * @param board       текущая доска.
     * @param pieceToMove фигура, для которой отображаются возможные ходы.
     */
    public void render(Board board, Piece pieceToMove) {
        Set<Coordinates> availableMoveSquares = emptySet();
        if (pieceToMove != null) {
            availableMoveSquares = pieceToMove.getAvailableMoveSquares(board);
        }

        for (int rank = 8; rank >= 1; rank--) {
            StringBuilder line = new StringBuilder();
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                boolean isHighlight = availableMoveSquares.contains(coordinates);

                if (board.isSquareEmpty(coordinates)) {
                    line.append(getSpriteForEmptySquare(coordinates, isHighlight));
                } else {
                    line.append(getPieceSprite(board.getPiece(coordinates), isHighlight));
                }
            }

            line.append(ANSI_RESET);
            System.out.println(line);
        }
    }

    /**
     * Рендерит шахматную доску без выделения фигур.
     *
     * @param board текущая доска.
     */
    public void render(Board board) {
        render(board, null);
    }

    /**
     * Применяет цветовую раскраску к спрайту клетки или фигуры.
     *
     * @param sprite        текст спрайта.
     * @param pieceColor    цвет фигуры.
     * @param isSquareDark  темная ли клетка.
     * @param isHighlight   выделена ли клетка.
     * @return цветной спрайт.
     */
    private String colorizeSprite(String sprite, Color pieceColor, boolean isSquareDark, boolean isHighlight) {
        String result = sprite;

        if (pieceColor == Color.WHITE) {
            result = ANSI_WHITE_PIECE_COLOR + result;
        } else {
            result = ANSI_BLACK_PIECE_COLOR + result;
        }

        if (isHighlight) {
            result = ANSI_HIGHLIGHTED_SQUARE_BACKGROUND + result;
        } else if (isSquareDark) {
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else {
            result = ANSI_WHITE_SQUARE_BACKGROUND + result;
        }

        return result;
    }

    /**
     * Возвращает значение для пустой клетки.
     *
     * @param coordinates координаты клетки.
     * @param isHighlight выделена ли клетка.
     * @return цветной спрайт пустой клетки.
     */
    private String getSpriteForEmptySquare(Coordinates coordinates, boolean isHighlight) {
        return colorizeSprite("   ", Color.WHITE, Board.isSquareDark(coordinates), isHighlight);
    }

    /**
     * Выбирает спрайт для фигуры на основе её типа.
     *
     * @param piece фигура.
     * @return спрайт фигуры.
     */
    private String selectUnicodeSpriteForPiece(Piece piece) {
        switch (piece.getClass().getSimpleName()) {
            case "Pawn":
                return "п";

            case "Knight":
                return "К";

            case "Bishop":
                return "С";

            case "Rook":
                return "Л";

            case "Queen":
                return "Ф";

            case "King":
                return "Й";

            default:
                return "";
        }
    }

    /**
     * Возвращает спрайт фигуры с цветовой раскраской.
     *
     * @param piece       фигура.
     * @param isHighlight выделена ли клетка.
     * @return цветной спрайт фигуры.
     */
    private String getPieceSprite(Piece piece, boolean isHighlight) {
        return colorizeSprite(
                " " + selectUnicodeSpriteForPiece(piece) + " ", piece.color, Board.isSquareDark(piece.coordinates),
                isHighlight
        );
    }
}
