package com.example.doangamecolat.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private boolean isGameOver;

    public Game(Player black, Player white) {
        this.board = new Board();
        this.blackPlayer = black;
        this.whitePlayer = white;
        this.currentPlayer = black;
    }


}
