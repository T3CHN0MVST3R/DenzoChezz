package com.denzo;

import com.denzo.board.Board;

public abstract class GameStateChecker {
    public abstract GameState check(Board board, Color color);
}
