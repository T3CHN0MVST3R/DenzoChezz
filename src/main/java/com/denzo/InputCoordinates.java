package com.denzo;

import com.denzo.board.Board;
import com.denzo.board.BoardFactory;
import com.denzo.board.Move;
import com.denzo.piece.*;
import com.denzo.BoardConsoleRenderer;

import java.util.Scanner;
import java.util.Set;

public class InputCoordinates {

    private static final Scanner scanner = new Scanner(System.in);

    public static Coordinates input() {
        while (true) {
            System.out.println("Please enter coordinates (ex. a1)");

            String line = scanner.nextLine().trim();

            if (line.length() != 2) {
                System.out.println("Invalid format");
                continue;
            }

            char fileChar = line.charAt(0);
            char rankChar = line.charAt(1);

            if (!Character.isLetter(fileChar)) {
                System.out.println("Invalid format");
                continue;
            }

            if (!Character.isDigit(rankChar)) {
                System.out.println("Invalid format");
                continue;
            }

            int rank = Character.getNumericValue(rankChar);
            if (rank < 1 || rank > 8) {
                System.out.println("Invalid rank");
                continue;
            }

            File file = File.fromChar(fileChar);
            if (file == null) {
                System.out.println("Invalid file");
                continue;
            }

            return new Coordinates(file, rank);
        }
    }

    public static Coordinates inputPieceCoordinatesForColor(Color color, Board board) {
        while (true) {
            System.out.println("Enter coordinates for a piece to move");
            Coordinates coordinates = input();

            if (board.isSquareEmpty(coordinates)) {
                System.out.println("Empty square");
                continue;
            }

            Piece piece = board.getPiece(coordinates);
            if (piece.color != color) {
                System.out.println("Wrong color");
                continue;
            }

            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);
            if (availableMoveSquares.isEmpty()) {
                System.out.println("Blocked piece");
                continue;
            }

            return coordinates;
        }
    }

    public static Coordinates inputAvailableSquare(Set<Coordinates> coordinates) {
        while (true) {
            System.out.println("Enter your move for selected piece");
            Coordinates input = input();

            if (!coordinates.contains(input)) {
                System.out.println("Non-available square");
                continue;
            }

            return input;
        }
    }

    // Обновленный метод inputMove
    public static Move inputMove(Board board, Color color, BoardConsoleRenderer renderer) {
        while (true) {
            Coordinates sourceCoordinates = inputPieceCoordinatesForColor(color, board);

            Piece piece = board.getPiece(sourceCoordinates);
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            renderer.render(board, piece);
            Coordinates targetCoordinates = inputAvailableSquare(availableMoveSquares);

            Move move;

            if (piece instanceof King) {
                int fileShift = targetCoordinates.file.ordinal() - sourceCoordinates.file.ordinal();
                if (Math.abs(fileShift) == 2) {
                    // Рокировка
                    move = new Move(sourceCoordinates, targetCoordinates);
                    move.isCastlingMove = true;
                    if (fileShift == 2) {
                        // Короткая рокировка
                        move.rookFrom = new Coordinates(File.H, sourceCoordinates.rank);
                        move.rookTo = new Coordinates(File.F, sourceCoordinates.rank);
                    } else if (fileShift == -2) {
                        // Длинная рокировка
                        move.rookFrom = new Coordinates(File.A, sourceCoordinates.rank);
                        move.rookTo = new Coordinates(File.D, sourceCoordinates.rank);
                    }
                } else {
                    move = new Move(sourceCoordinates, targetCoordinates);
                }
            } else {
                move = new Move(sourceCoordinates, targetCoordinates);
            }

            // Добавляем обработку превращения пешки
            if (piece instanceof Pawn) {
                int lastRank = color == Color.WHITE ? 8 : 1;
                if (targetCoordinates.rank == lastRank) {
                    Class<? extends Piece> promotionPieceType = inputPromotionPieceType();
                    move.promotionPieceType = promotionPieceType;
                }
            }

            if (validateIfKingInCheckAfterMove(board, color, move)) {
                System.out.println("Ваш король под атакой!");
                continue;
            }

            return move;
        }
    }

    private static Class<? extends Piece> inputPromotionPieceType() {
        while (true) {
            System.out.println("Выберите фигуру для превращения (Q - Ферзь, R - Ладья, B - Слон, N - Конь):");
            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "Q":
                    return Queen.class;
                case "R":
                    return Rook.class;
                case "B":
                    return Bishop.class;
                case "N":
                    return Knight.class;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
            }
        }
    }

    private static boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
        Board copy = new BoardFactory().copy(board);
        copy.makeMove(move);

        Piece king = copy.getPiecesByColor(color).stream()
                .filter(p -> p instanceof King)
                .findFirst()
                .orElse(null);

        if (king == null) {
            throw new RuntimeException("King not found on the board!");
        }

        return copy.isSquareAttackedByColor(king.coordinates, color.opposite());
    }
}
