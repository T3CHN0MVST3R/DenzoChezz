package com.denzo.piece;

import com.denzo.Color;
import com.denzo.Coordinates;

/**
 * Класс для создания шахматных фигур.
 *
 * <p>
 * Этот класс предоставляет методы для создания экземпляров фигур на основе символов FEN
 * или типа фигуры, цвета и координат. Он используется для инициализации доски
 * и обработки превращений пешек.
 * </p>
 */
public class PieceFactory {

    /**
     * Создает фигуру на основе символа FEN.
     *
     * <p>
     * Символы FEN (Forsyth-Edwards Notation) используются для представления расположения фигур на шахматной доске.
     * Этот метод принимает символ FEN и координаты на доске, после чего возвращает соответствующую фигуру.
     * </p>
     *
     * @param fenChar символ FEN, представляющий фигуру.
     * @param coordinates координаты фигуры на доске.
     * @return созданная фигура.
     * @throws IllegalArgumentException если символ FEN неизвестен.
     */
    public Piece fromFenChar(char fenChar, Coordinates coordinates) {
        switch (fenChar) {
            case 'p':
                // Черная пешка
                return new Pawn(Color.BLACK, coordinates);
            case 'P':
                // Белая пешка
                return new Pawn(Color.WHITE, coordinates);

            case 'r':
                // Черная ладья
                return new Rook(Color.BLACK, coordinates);
            case 'R':
                // Белая ладья
                return new Rook(Color.WHITE, coordinates);

            case 'n':
                // Черный конь
                return new Knight(Color.BLACK, coordinates);
            case 'N':
                // Белый конь
                return new Knight(Color.WHITE, coordinates);

            case 'b':
                // Черный слон
                return new Bishop(Color.BLACK, coordinates);
            case 'B':
                // Белый слон
                return new Bishop(Color.WHITE, coordinates);

            case 'q':
                // Черный ферзь
                return new Queen(Color.BLACK, coordinates);
            case 'Q':
                // Белый ферзь
                return new Queen(Color.WHITE, coordinates);

            case 'k':
                // Черный король
                return new King(Color.BLACK, coordinates);
            case 'K':
                // Белый король
                return new King(Color.WHITE, coordinates);

            default:
                // Неизвестный символ FEN
                throw new IllegalArgumentException("Unknown FEN character: " + fenChar);
        }
    }

    /**
     * Создает фигуру на основе типа фигуры, цвета и координат.
     *
     * <p>
     * Этот метод используется для создания фигур при превращении пешки или других случаях,
     * когда необходимо динамически создать фигуру определенного типа.
     * </p>
     *
     * @param pieceType тип фигуры.
     * @param color цвет фигуры.
     * @param coordinates координаты фигуры на доске.
     * @return созданная фигура.
     * @throws IllegalArgumentException если тип фигуры неизвестен.
     */
    public Piece createPiece(PieceType pieceType, Color color, Coordinates coordinates) {
        switch (pieceType) {
            case PAWN:
                // Пешка
                return new Pawn(color, coordinates);
            case KNIGHT:
                // Конь
                return new Knight(color, coordinates);
            case BISHOP:
                // Слон
                return new Bishop(color, coordinates);
            case ROOK:
                // Ладья
                return new Rook(color, coordinates);
            case QUEEN:
                // Ферзь
                return new Queen(color, coordinates);
            case KING:
                // Король
                return new King(color, coordinates);
            default:
                // Неизвестный тип фигуры
                throw new IllegalArgumentException("Unknown PieceType: " + pieceType);
        }
    }
}
