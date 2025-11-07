package com.example.doangamecolat.model;

public class HumanPlayer extends Player {

    public HumanPlayer(Piece color) {
        super(color);
    }

    @Override
    public Move getMove(Board board) {
        // Tùy vào giao diện (JavaFX) – tạm thời để null
        return null;
    }
}
