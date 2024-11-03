package com.denzo.board;

import com.denzo.Color;
import com.denzo.Coordinates;
import com.denzo.piece.*;
import com.denzo.piece.PieceFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс, представляющий шахматную доску.
 */
public class Board {
    private final String startingFen;
    private final HashMap<Coordinates, Piece> pieces = new HashMap<>();
    private final List<Move> moves = new ArrayList<>();
    private Move lastMove = null; // Поле для хранения последнего хода
    private final PieceFactory pieceFactory = new PieceFactory(); // Объект для создания фигур

    /**
     * Конструктор доски.
     *
     * @param startingFen строка FEN, представляющая начальное состояние доски.
     */
    public Board(String startingFen) {
        this.startingFen = startingFen;
        // Инициализация доски из FEN (необходимо реализовать)
        // fromFEN(startingFen);
    }

    /**
     * Устанавливает фигуру на указанные координаты.
     *
     * @param coordinates координаты на доске.
     * @param piece       фигура, которую нужно установить.
     */
    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        pieces.put(coordinates, piece);
    }

    /**
     * Удаляет фигуру с указанных координат.
     *
     * @param coordinates координаты на доске.
     */
    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }

    /**
     * Выполняет ход на доске с обработкой рокировки и взятия En Passant.
     *
     * @param move объект, представляющий ход.
     */
    public void makeMove(Move move) {
        Piece piece = getPiece(move.from);
        move.pieceMoved = piece; // Устанавливаем перемещённую фигуру

        // Удаление фигуры с исходной позиции
        removePiece(move.from);

        // Проверка на превращение пешки
        if (piece instanceof Pawn) {
            int lastRank = piece.color == Color.WHITE ? 8 : 1;
            if (move.to.rank == lastRank) {
                // Превращение пешки
                Piece promotedPiece;
                if (move.promotionPieceType != null) {
                    promotedPiece = pieceFactory.createPiece(move.promotionPieceType, piece.color, move.to);
                } else {
                    // По умолчанию превращаем в ферзя
                    promotedPiece = new Queen(piece.color, move.to);
                }
                setPiece(move.to, promotedPiece);
            } else {
                // Обычный ход пешки
                setPiece(move.to, piece);
                piece.coordinates = move.to;
            }
        } else {
            // Обычный ход фигуры
            setPiece(move.to, piece);
            piece.coordinates = move.to;
        }

        piece.hasMoved = true; // Обновление флага hasMoved

        // Обработка рокировки
        if (move.isCastlingMove) {
            // Перемещение ладьи при рокировке
            Piece rook = getPiece(move.rookFrom);
            removePiece(move.rookFrom);
            setPiece(move.rookTo, rook);

            rook.coordinates = move.rookTo;
            rook.hasMoved = true;
        }

        // Обработка взятия En Passant
        if (move.isEnPassantMove && piece instanceof Pawn) {
            int direction = piece.color == Color.WHITE ? -1 : 1;
            Coordinates capturedPawnCoords = new Coordinates(move.to.file, move.to.rank + direction);
            removePiece(capturedPawnCoords);
        }

        // Обновление последнего хода
        lastMove = move;

        // Добавление хода в список
        moves.add(move);
    }

    /**
     * Проверяет, пуста ли указанная клетка.
     *
     * @param coordinates координаты на доске.
     * @return true, если клетка пуста, иначе false.
     */
    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

    /**
     * Возвращает фигуру, находящуюся на указанных координатах.
     *
     * @param coordinates координаты на доске.
     * @return фигура или null, если клетка пуста.
     */
    public Piece getPiece(Coordinates coordinates) {
        return pieces.get(coordinates);
    }

    /**
     * Проверяет, является ли клетка темной.
     *
     * @param coordinates координаты на доске.
     * @return true, если клетка темная, иначе false.
     */
    public static boolean isSquareDark(Coordinates coordinates) {
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
    }

    /**
     * Возвращает список фигур указанного цвета.
     *
     * @param color цвет фигур.
     * @return список фигур.
     */
    public List<Piece> getPiecesByColor(Color color) {
        List<Piece> result = new ArrayList<>();

        for (Piece piece : pieces.values()) {
            if (piece.color == color) {
                result.add(piece);
            }
        }

        return result;
    }

    /**
     * Проверяет, атакована ли указанная клетка фигурами заданного цвета.
     *
     * @param coordinates координаты на доске.
     * @param color       цвет атакующих фигур.
     * @return true, если клетка атакована, иначе false.
     */
    public boolean isSquareAttackedByColor(Coordinates coordinates, Color color) {
        List<Piece> pieces = getPiecesByColor(color);

        for (Piece piece : pieces) {
            Set<Coordinates> attackedSquares = piece.getAttackedSquares(this);

            if (attackedSquares.contains(coordinates)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Возвращает последний выполненный ход.
     *
     * @return последний ход или null, если ходов не было.
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Возвращает начальную строку FEN.
     *
     * @return строка FEN.
     */
    public String getStartingFen() {
        return startingFen;
    }

    /**
     * Возвращает неизменяемую карту фигур на доске.
     *
     * @return карта фигур.
     */
    public Map<Coordinates, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    /**
     * Возвращает список всех выполненных ходов.
     *
     * @return список ходов.
     */
    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }
}
