package com.denzo.board;

import com.denzo.Coordinates;
import com.denzo.piece.Piece;
import com.denzo.piece.PieceType;

/**
 * Класс, представляющий ход в шахматной игре.
 */
public class Move {
    /**
     * Координаты исходной клетки, откуда перемещается фигура.
     */
    public final Coordinates from;

    /**
     * Координаты целевой клетки, куда перемещается фигура.
     */
    public final Coordinates to;

    /**
     * Флаг, указывающий, является ли данный ход рокировкой.
     */
    public boolean isCastlingMove = false;

    /**
     * Координаты ладьи при рокировке.
     * Используются только если {@code isCastlingMove} равно {@code true}.
     */
    public Coordinates rookFrom, rookTo;

    /**
     * Флаг, указывающий, является ли данный ход взятием En Passant.
     */
    public boolean isEnPassantMove = false; // Поле для En Passant

    /**
     * Тип фигуры, в которую превращается пешка при достижении последней горизонтали.
     * Используется только если пешка достигает последней горизонтали.
     */
    public PieceType promotionPieceType = null; // Поле для превращения

    /**
     * Фигура, которая была перемещена в ходе данного хода.
     * Используется для отслеживания перемещенной фигуры и её свойств.
     */
    public Piece pieceMoved; // Поле для хранения перемещённой фигуры

    /**
     * Конструктор хода.
     *
     * @param from координаты исходной клетки.
     * @param to   координаты целевой клетки.
     */
    public Move(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }
}
