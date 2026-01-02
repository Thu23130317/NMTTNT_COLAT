package com.example.doangamecolat.model;

import com.example.doangamecolat.ai.MiniMax; // Nhớ import class MiniMax

import java.util.List;

public class AIPlayer extends Player {
    private int maxDepth;

    public AIPlayer(Piece color, int maxDepth) {
        super(color);
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getMove(Board board) {
        // Nếu độ sâu là 0 hoặc 1 (Mức siêu dễ), đánh random cho vui
        if (this.maxDepth <= 1) {
            List<Move> moves = board.getValidMoves(this.pieceColor);
            if (moves.isEmpty()) return null;
            // Chọn bừa một nước
            return moves.get((int) (Math.random() * moves.size()));
        }

        // Các mức khó hơn thì dùng não (Minimax)
        return MiniMax.findBestMove(board, this.pieceColor, this.maxDepth);
    }
}