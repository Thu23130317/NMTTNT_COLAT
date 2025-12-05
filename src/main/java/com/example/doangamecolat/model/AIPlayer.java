package com.example.doangamecolat.model;

import com.example.doangamecolat.ai.Heuristic;
import com.example.doangamecolat.ai.MiniMax;

import java.util.List;

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
        return MiniMax.findBestMove(board, this.pieceColor, this.maxDepth);
    }
}
