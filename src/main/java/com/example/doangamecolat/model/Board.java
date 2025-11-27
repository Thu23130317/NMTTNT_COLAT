package com.example.doangamecolat.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public static final int SIZE = 8;
    private Piece[][] grid;
    private static final int[][] DIRECTIONS = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Board() {
        grid = new Piece[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = Piece.EMPTY;
            }
        }
        grid[3][3] = Piece.WHITE;
        grid[3][4] = Piece.BLACK;
        grid[4][3] = Piece.BLACK;
        grid[4][4] = Piece.WHITE;
    }
    public Board getCopy() {
        Board newBoard = new Board();


        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newBoard.grid[i][j] = this.grid[i][j];
            }
        }
        return newBoard;
    }

    public Piece getPiece(int row, int col) {
        if (!isPieceInChessboard(row, col)) {
            return null;
        }
        return grid[row][col];
    }
    private boolean isPieceInChessboard(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
    public List<Move> getValidMoves(Piece playerColor) {
        List<Move> validMoves = new ArrayList<>();
        Piece opponentColor = playerColor.opposite();

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] != Piece.EMPTY) {
                    continue;
                }

                List<int[]> flippedByThisMove = new ArrayList<>();

                for (int[] dir : DIRECTIONS) {
                    int dr = dir[0];
                    int dc = dir[1];

                    List<int[]> canFlips = new ArrayList<>();
                    int cr = r + dr;
                    int cc = c + dc;

                    while (isPieceInChessboard(cr, cc)) {
                        Piece pieceCurrent = grid[cr][cc];

                        if (pieceCurrent == opponentColor) {
                            canFlips.add(new int[]{cr, cc});
                        } else if (pieceCurrent == playerColor) {
                            flippedByThisMove.addAll(canFlips);
                            break;
                        } else {
                            break;
                        }
                        cr += dr;
                        cc += dc;
                    }
                }

                if (!flippedByThisMove.isEmpty()) {
                    validMoves.add(new Move(r, c, flippedByThisMove));
                }
            }
        }
        return validMoves;
    }
    public void makeMove(Move move, Piece playerColor) {
        grid[move.getRow()][move.getCol()] = playerColor;

        for (int[] pos : move.getFlippedDiscs()) {
            grid[pos[0]][pos[1]] = playerColor;
        }
    }
    public Map<Piece, Integer> getScore() {
        Map<Piece, Integer> scores = new HashMap<>();
        scores.put(Piece.BLACK, 0);
        scores.put(Piece.WHITE, 0);
        scores.put(Piece.EMPTY, 0);

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Piece p = grid[r][c];
                scores.put(p, scores.get(p) + 1);
            }
        }
        return scores;
    }
}
