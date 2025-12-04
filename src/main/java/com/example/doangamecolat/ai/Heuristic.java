package com.example.doangamecolat.ai;


import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Piece;

public class Heuristic {

    private static final int[][] WEIGHTS = {
            {100, -20, 10,  5,  5, 10, -20, 100},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            { 10,  -2, -1, -1, -1, -1,  -2,  10},
            {  5,  -2, -1, -1, -1, -1,  -2,   5},
            {  5,  -2, -1, -1, -1, -1,  -2,   5},
            { 10,  -2, -1, -1, -1, -1,  -2,  10},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {100, -20, 10,  5,  5, 10, -20, 100}
    };

    public static int evaluate(Board board, Piece playerColor) {
        int score = 0;
        Piece opponentColor = playerColor.opposite();

        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                Piece p = board.getPiece(i, j);
                if (p == playerColor) {
                    score += WEIGHTS[i][j];
                } else if (p == opponentColor) {
                    score -= WEIGHTS[i][j];
                }
            }
        }
        return score;
    }
}
