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
        this.currentPlayer = black;
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
            return new ArrayList<>();
        }
        return board.getValidMoves(currentPlayer.getPieceColor());
    }
    public boolean playTurn(int row, int col) {
        if (isGameOver) return false;

        List<Move> validMoves = board.getValidMoves(currentPlayer.getPieceColor());
        Move moveToMake = null;
        for (Move m : validMoves) {
            if (m.getRow() == row && m.getCol() == col) {
                moveToMake = m;
                break;
            }
        }

        if (moveToMake == null) {
            return false;
        }

        board.makeMove(moveToMake, currentPlayer.getPieceColor());

        nextTurn();
        return true;
    }
    public void nextTurn() {
        switchPlayer();

        if (board.getValidMoves(currentPlayer.getPieceColor()).isEmpty()) {
            System.out.println(currentPlayer.getPieceColor() + " không có nước đi. Chuyển lượt lại.");
            switchPlayer();

            if (board.getValidMoves(currentPlayer.getPieceColor()).isEmpty()) {
                isGameOver = true;
                System.out.println("GAME OVER!");
            }
        }
    }
    public int getScore(Piece color) {
        return board.getScore().get(color);
    }
}
