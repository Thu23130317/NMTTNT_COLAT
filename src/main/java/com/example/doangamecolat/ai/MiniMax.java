package com.example.doangamecolat.ai;

import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Move;
import com.example.doangamecolat.model.Piece;

import java.util.List;

public class MiniMax {
    public static Move findBestMove(Board board, Piece aiColor, int maxDepth) {
        List<Move> validMoves = board.getValidMoves(aiColor);
        if (validMoves.isEmpty()) return null;

        Move bestMove = null;
        int maxEval = Integer.MIN_VALUE;

        for (Move move : validMoves) {
            Board copyBoard = board.getCopy();
            copyBoard.makeMove(move, aiColor);

            int eval = minimax(copyBoard, maxDepth - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, aiColor);

            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta, Piece aiColor) {
        if (depth == 0) {
            return Heuristic.evaluate(board, aiColor);
        }

        Piece currentPlayer = isMaximizing ? aiColor : aiColor.opposite();
        List<Move> moves = board.getValidMoves(currentPlayer);

        if (moves.isEmpty()) {
            if (board.getValidMoves(currentPlayer.opposite()).isEmpty()) {
                return Heuristic.evaluate(board, aiColor);
            }
            return minimax(board, depth - 1, !isMaximizing, alpha, beta, aiColor);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);
                int eval = minimax(copy, depth - 1, false, alpha, beta, aiColor);
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
                int eval = minimax(copy, depth - 1, true, alpha, beta, aiColor);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }
}
