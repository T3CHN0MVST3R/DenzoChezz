package com.denzo.board;

import com.denzo.Coordinates;
import com.denzo.File;
import com.denzo.piece.PieceFactory;

/**
 * Класс для создания и копирования доски.
 */
public class BoardFactory {

    private final PieceFactory pieceFactory = new PieceFactory();

    /**
     * Создаёт доску на основе строки FEN.
     *
     * @param fen строка FEN, представляющая начальное состояние доски.
     * @return созданная доска.
     */
    public Board fromFEN(String fen) {
        Board board = new Board(fen);

        String[] parts = fen.split(" ");
        String piecePositions = parts[0];

        String[] fenRows = piecePositions.split("/");

        for (int i = 0; i < fenRows.length; i++) {
            String row = fenRows[i];
            int rank = 8 - i;

            int fileIndex = 0;
            for (int j = 0; j < row.length(); j++) {
                char fenChar = row.charAt(j);

                if (Character.isDigit(fenChar)) {
                    fileIndex += Character.getNumericValue(fenChar);
                } else {
                    File file = File.values()[fileIndex];
                    Coordinates coordinates = new Coordinates(file, rank);

                    board.setPiece(coordinates, pieceFactory.fromFenChar(fenChar, coordinates));
                    fileIndex++;
                }
            }
        }

        return board;
    }

    /**
     * Создаёт копию доски.
     *
     * @param source исходная доска.
     * @return клонированная доска.
     */
    public Board copy(Board source) {
        // Используем геттер для получения startingFen
        Board clone = fromFEN(source.getStartingFen());

        // Используем геттер для получения списка ходов
        for (Move move : source.getMoves()) {
            clone.makeMove(move);
        }

        return clone;
    }
}
