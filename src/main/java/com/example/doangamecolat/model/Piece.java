package com.example.doangamecolat.model;

public enum Piece {
    BLACK,
    WHITE,
    EMPTY;

    public Piece opposite() {
        if (this == BLACK) return WHITE;
        if (this == WHITE) return BLACK;
        return EMPTY;
    }

    public int toInt() {
        return this == BLACK ? 1 : this == WHITE ? -1 : 0;
    }
}
