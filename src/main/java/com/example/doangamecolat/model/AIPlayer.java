package com.example.doangamecolat.model;

import com.example.doangamecolat.ai.Heuristic;

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
        List<Move> validMoves = board.getValidMoves(color);
        if (validMoves.isEmpty()) return null;

        Move bestMove = null;
        int maxEval = Integer.MIN_VALUE;

        for (Move move : validMoves) {
            Board copyBoard = board.getCopy();
            copyBoard.makeMove(move, color);

            // Gọi đệ quy Minimax
            int eval = minimax(copyBoard, maxDepth - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
        }
        return bestMove;
    }
    private int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta) {
        // Điều kiện dừng: Hết độ sâu
        if (depth == 0) {
            // GỌI HÀM TỪ FILE HEURISTIC.JAVA
            return Heuristic.evaluate(board, this.pieceColor);
        }

        Piece currentPlayer = isMaximizing ? this.pieceColor : this.pieceColor.opposite();
        List<Move> moves = board.getValidMoves(currentPlayer);

        if (moves.isEmpty()) {
            if (board.getValidMoves(currentPlayer.opposite()).isEmpty()) {
                // Game over
                return Heuristic.evaluate(board, this.pieceColor);
            }
            // Pass turn
            return minimax(board, depth - 1, !isMaximizing, alpha, beta);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);
                int eval = minimax(copy, depth - 1, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);
                int eval = minimax(copy, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }
}
