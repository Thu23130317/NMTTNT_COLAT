package com.example.doangamecolat.model;

public class Board {
    public static final int SIZE = 8;
    private Piece[][] grid;
    // 8 hướng di chuyển (ngang, dọc, chéo)
    private static final int[][] DIRECTIONS = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

}
