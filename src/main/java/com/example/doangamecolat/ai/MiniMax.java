package com.example.doangamecolat.ai;

import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Move;
import com.example.doangamecolat.model.Piece;

import java.util.List;

public class MiniMax {

    public static Move findBestMove(Board board, Piece aiColor, int depth) {

        List<Move> moves = board.getValidMoves(aiColor);
        if (moves.isEmpty()) return null;

        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        for (Move move : moves) {
            Board copy = board.getCopy();
            copy.makeMove(move, aiColor);

            int value = minimax(false, copy, depth - 1, aiColor);

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static int minimax(boolean isMax, Board board, int depth, Piece aiColor) {


        if (depth == 0 || board.isGameOver()) {
            return Heuristic.evaluate(board, aiColor);
        }

        Piece currentPlayer = isMax ? aiColor : aiColor.opposite();
        List<Move> moves = board.getValidMoves(currentPlayer);


        if (moves.isEmpty()) {
            return minimax(!isMax, board, depth - 1, aiColor);
        }


        if (isMax) {
            int temp = Integer.MIN_VALUE;

            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);

                int value = minimax(false, copy, depth - 1, aiColor);
                if (value > temp) {
                    temp = value;
                }
            }
            return temp;
        }


        else {
            int temp = Integer.MAX_VALUE;

            for (Move move : moves) {
                Board copy = board.getCopy();
                copy.makeMove(move, currentPlayer);

                int value = minimax(true, copy, depth - 1, aiColor);
                if (value < temp) {
                    temp = value;
                }
            }
            return temp;
        }
    }
}
