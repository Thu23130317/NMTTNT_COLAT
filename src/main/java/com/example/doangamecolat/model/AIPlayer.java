package com.example.doangamecolat.model;

public class AIPlayer extends Player {
    private int maxDepth;

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
