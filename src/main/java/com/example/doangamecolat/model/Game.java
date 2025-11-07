package com.example.doangamecolat.model;

public class Game {
    private Board board;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;

    public Game(Player black, Player white) {
        this.board = new Board();
        this.blackPlayer = black;
        this.whitePlayer = white;
        this.currentPlayer = black;
    }


}
