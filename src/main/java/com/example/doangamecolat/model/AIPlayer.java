package com.example.doangamecolat.model;

import java.util.List;

import com.example.doangamecolat.ai.AlphaBeta;
import com.example.doangamecolat.ai.MiniMax;

/**
 * AI Player - chơi bằng các thuật toán AI
 * Hỗ trợ cả MiniMax và AlphaBeta
 */
public class AIPlayer extends Player {
    private int maxDepth;
    private boolean useAlphaBeta;

    /**
     * Constructor với loại AI mặc định là MiniMax
     */
    public AIPlayer(Piece color, int maxDepth) {
        this(color, maxDepth, false);
    }

    /**
     * Constructor với loại AI tùy chọn
     * @param color: màu cờ của AI
     * @param maxDepth: độ sâu tìm kiếm
     * @param useAlphaBeta: true nếu dùng AlphaBeta, false nếu dùng MiniMax
     */
    public AIPlayer(Piece color, int maxDepth, boolean useAlphaBeta) {
        super(color);
        this.maxDepth = maxDepth;
        this.useAlphaBeta = useAlphaBeta;
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

        // Các mức khó hơn thì dùng AI
        if (useAlphaBeta) {
            return AlphaBeta.findBestMove(board, this.pieceColor, this.maxDepth);
        } else {
            return MiniMax.findBestMove(board, this.pieceColor, this.maxDepth);
        }
    }
}
