package com.example.doangamecolat.ai;

import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Move;
import com.example.doangamecolat.model.Piece;

import java.util.List;

public class AlphaBeta {

    // Hàm gọi chính từ bên ngoài
    public static Move findBestMove(Board board, Piece aiColor, int depth) {
        List<Move> moves = board.getValidMoves(aiColor);
        if (moves.isEmpty()) return null;

        Move bestMove = null;
        int maxEval = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        // Duyệt qua các nước đi lớp đầu tiên
        for (Move move : moves) {
            Board copy = board.getCopy();
            copy.makeMove(move, aiColor);

            // Gọi đệ quy: đến lượt đối thủ (isMax = false)
            int eval = alphaBeta(copy, depth - 1, alpha, beta, false, aiColor);

            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }

            // Cập nhật Alpha (giá trị tốt nhất AI đã tìm thấy)
            alpha = Math.max(alpha, eval);
        }
        return bestMove;
    }

    // Hàm đệ quy Alpha-Beta
    private static int alphaBeta(Board board, int depth, int alpha, int beta, boolean isMax, Piece aiColor) {
        // 1. Điều kiện dừng: Hết độ sâu hoặc Game Over
        if (depth == 0 || board.isGameOver()) {
            return Heuristic.evaluate(board, aiColor);
        }

        Piece currentPlayer = isMax ? aiColor : aiColor.opposite();
        List<Move> moves = board.getValidMoves(currentPlayer);

        // 2. Xử lý trường hợp không có nước đi (Pass lượt)
        if (moves.isEmpty()) {
            // Giữ nguyên bàn cờ, chuyển lượt cho đối thủ, không giảm depth
            return alphaBeta(board, depth, alpha, beta, !isMax, aiColor);
        }

        if (isMax) {
            // Lượt của AI (Muốn điểm cao nhất)
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);

                int eval = alphaBeta(copy, depth - 1, alpha, beta, false, aiColor);
                maxEval = Math.max(maxEval, eval);

                // Cập nhật Alpha
                alpha = Math.max(alpha, eval);

                // Cắt tỉa Beta
                if (beta <= alpha) {
                    break; // Đối thủ sẽ không bao giờ cho phép nhánh này xảy ra
                }
            }
            return maxEval;

        } else {
            // Lượt của Người (Giả định người chơi thông minh, muốn điểm AI thấp nhất)
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);

                int eval = alphaBeta(copy, depth - 1, alpha, beta, true, aiColor);
                minEval = Math.min(minEval, eval);

                // Cập nhật Beta
                beta = Math.min(beta, eval);

                // Cắt tỉa Alpha
                if (beta <= alpha) {
                    break; // AI sẽ không bao giờ chọn nhánh này
                }
            }
            return minEval;
        }
    }
}