package com.example.doangamecolat.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private boolean isGameOver;

    public Game(Player black, Player white) {
        this.board = new Board();
        this.blackPlayer = black;
        this.whitePlayer = white;
        this.currentPlayer = black; // Theo luật, Đen luôn đi trước
        this.isGameOver = false;
    }
    public Board getBoard() {
        return board;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean isGameOver() {
        if (isGameOver) return true;

        List<Move> blackMoves = board.getValidMoves(Piece.BLACK);
        List<Move> whiteMoves = board.getValidMoves(Piece.WHITE);

        if (blackMoves.isEmpty() && whiteMoves.isEmpty()) {
            isGameOver = true;
            return true;
        }
        return false;
    }
    private void switchPlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }
    }
    public List<Move> getValidMovesCurrentPlayer() {
        if (isGameOver()) {
            return new ArrayList<>(); // Trả về danh sách rỗng nếu game kết thúc
        }
        return board.getValidMoves(currentPlayer.getPieceColor());
    }

}
