package com.example.doangamecolat.model;

import java.util.List;

public class Move {
    private List<int[]> flipped;
    private int row;
    private int col;

    public Move(int row, int col, List<int[]> flipped) {
        this.row = row;
        this.col = col;
        this.flipped = flipped;
    }
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<int[]> getFlippedDiscs() {
        return flipped;
    }
}
