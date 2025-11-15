package com.example.doangamecolat.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public static final int SIZE = 8;
    private Piece[][] grid;
    // 8 hướng di chuyển (ngang, dọc, chéo)
    private static final int[][] DIRECTIONS = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

}
