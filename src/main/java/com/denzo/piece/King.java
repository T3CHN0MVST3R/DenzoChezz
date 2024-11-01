package com.denzo.piece;

import com.denzo.board.Board;
import com.denzo.Color;
import com.denzo.Coordinates;
import com.denzo.File;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public King(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        for (int fileShift = -1; fileShift <= 1; fileShift++) {
            for (int rankShift = -1; rankShift <= 1; rankShift++) {
                if ((fileShift == 0) && (rankShift == 0)) {
                    continue;
                }

                result.add(new CoordinatesShift(fileShift, rankShift));
            }
        }

        return result;
    }

    @Override
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        boolean result = super.isSquareAvailableForMove(coordinates, board);

        if (result) {
            return !board.isSquareAttackedByColor(coordinates, color.opposite());
        }

        return false;
    }

    @Override
    public Set<Coordinates> getAvailableMoveSquares(Board board) {
        Set<Coordinates> result = super.getAvailableMoveSquares(board);

        if (!hasMoved && !board.isSquareAttackedByColor(this.coordinates, color.opposite())) {
            // Проверка возможности короткой рокировки
            if (canCastleKingSide(board)) {
                result.add(new Coordinates(File.G, this.coordinates.rank));
            }

            // Проверка возможности длинной рокировки
            if (canCastleQueenSide(board)) {
                result.add(new Coordinates(File.C, this.coordinates.rank));
            }
        }

        return result;
    }

    private boolean canCastleKingSide(Board board) {
        // Проверка, что ладья не двигалась и стоит на правильной позиции
        Coordinates rookCoordinates = new Coordinates(File.H, this.coordinates.rank);
        Piece rook = board.getPiece(rookCoordinates);
        if (rook == null || rook.hasMoved || !(rook instanceof Rook) || rook.color != this.color) {
            return false;
        }

        // Проверка, что клетки между королем и ладьей пусты
        Coordinates fSquare = new Coordinates(File.F, this.coordinates.rank);
        Coordinates gSquare = new Coordinates(File.G, this.coordinates.rank);
        if (!board.isSquareEmpty(fSquare) || !board.isSquareEmpty(gSquare)) {
            return false;
        }

        // Проверка, что клетки не находятся под атакой
        if (board.isSquareAttackedByColor(fSquare, color.opposite()) ||
                board.isSquareAttackedByColor(gSquare, color.opposite())) {
            return false;
        }

        return true;
    }

    private boolean canCastleQueenSide(Board board) {
        // Проверка, что ладья не двигалась и стоит на правильной позиции
        Coordinates rookCoordinates = new Coordinates(File.A, this.coordinates.rank);
        Piece rook = board.getPiece(rookCoordinates);
        if (rook == null || rook.hasMoved || !(rook instanceof Rook) || rook.color != this.color) {
            return false;
        }

        // Проверка, что клетки между королем и ладьей пусты
        Coordinates bSquare = new Coordinates(File.B, this.coordinates.rank);
        Coordinates cSquare = new Coordinates(File.C, this.coordinates.rank);
        Coordinates dSquare = new Coordinates(File.D, this.coordinates.rank);
        if (!board.isSquareEmpty(bSquare) || !board.isSquareEmpty(cSquare) || !board.isSquareEmpty(dSquare)) {
            return false;
        }

        // Проверка, что клетки не находятся под атакой
        if (board.isSquareAttackedByColor(dSquare, color.opposite()) ||
                board.isSquareAttackedByColor(cSquare, color.opposite())) {
            return false;
        }

        return true;
    }
}
