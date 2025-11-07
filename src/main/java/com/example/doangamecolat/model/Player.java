package com.example.doangamecolat.model;

public abstract class Player {
    protected Piece pieceColor;

    public Player(Piece color) {
        this.pieceColor = color;
    }

    public Piece getPieceColor() {
        return pieceColor;
    }
    public abstract Move getMove(Board board);
}
