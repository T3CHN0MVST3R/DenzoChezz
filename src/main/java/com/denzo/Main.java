package com.denzo;

import com.denzo.board.Board;
import com.denzo.board.BoardFactory;

public class Main {
    public static void main(String[] args) {
        Board board = (new BoardFactory()).fromFEN(
                "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        );

        Game game = new Game(board);
        game.gameLoop();
    }
}