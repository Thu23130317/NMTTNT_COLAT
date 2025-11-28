package com.example.doangamecolat.model;

public class AIPlayer extends Player {
    private int maxDepth;
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
    public AIPlayer(Piece color, int maxDepth) {
        super(color);
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getMove(Board board) {
        return findBestMove(board, this.pieceColor);
    }

    private Move findBestMove(Board board, Piece color) {
        return null;
    }
}
