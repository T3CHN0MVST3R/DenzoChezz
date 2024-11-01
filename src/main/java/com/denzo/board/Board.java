package com.denzo.board;

import com.denzo.Color;
import com.denzo.Coordinates;
import com.denzo.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Board {
    public final String startingFen;
    private HashMap<Coordinates, Piece> pieces = new HashMap<>();

    public List<Move> moves = new ArrayList<>();

    public Board(String startingFen) {
        this.startingFen = startingFen;
    }

    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        pieces.put(coordinates, piece);
    }

    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }

    // Обновленный метод makeMove
    public void makeMove(Move move) {
        Piece piece = getPiece(move.from);

        removePiece(move.from);
        setPiece(move.to, piece);

        piece.hasMoved = true; // Обновление флага

        if (move.isCastlingMove) {
            // Перемещение ладьи при рокировке
            Piece rook = getPiece(move.rookFrom);
            removePiece(move.rookFrom);
            setPiece(move.rookTo, rook);

            rook.hasMoved = true;
        }

        moves.add(move);
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

    public Piece getPiece(Coordinates coordinates) {
        return pieces.get(coordinates);
    }

    public static boolean isSquareDark(Coordinates coordinates) {
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
    }

    public List<Piece> getPiecesByColor(Color color) {
        List<Piece> result = new ArrayList<>();

        for (Piece piece : pieces.values()) {
            if (piece.color == color) {
                result.add(piece);
            }
        }

        return result;
    }

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
}
