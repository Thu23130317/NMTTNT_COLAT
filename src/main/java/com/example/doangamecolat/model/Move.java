package com.example.doangamecolat.model;

import java.util.List;

public class Move {
    private List<int[]> flippedDiscs;
    private int row;
    private int col;

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
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
}
