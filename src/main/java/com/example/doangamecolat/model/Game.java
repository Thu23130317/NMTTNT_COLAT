package com.example.doangamecolat.model;

import java.util.List;
import java.util.Stack;

public class Game {
    private Board board;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private boolean isGameOver;
    private Stack<Move> moveHistory; // Lưu lịch sử moves để undo

    public Game(Player black, Player white) {
        this.board = new Board();
        this.blackPlayer = black;
        this.whitePlayer = white;
        this.currentPlayer = black;
        this.isGameOver = false;
        this.moveHistory = new Stack<>();
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

    public void switchPlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }
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
        moveHistory.push(moveToMake); // Lưu move vào history

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
    
    // Lấy danh sách valid moves của player hiện tại
    public List<Move> getValidMovesForCurrentPlayer() {
        return board.getValidMoves(currentPlayer.getPieceColor());
    }
    
    // Undo move cuối cùng
    public boolean undoLastMove() {
        if (moveHistory.isEmpty() || board == null) {
            return false;
        }
        
        // Tạo board từ lịch sử (replay tất cả moves trừ move cuối)
        Board newBoard = new Board();
        Stack<Move> tempHistory = new Stack<>();
        
        while (!moveHistory.isEmpty()) {
            tempHistory.push(moveHistory.pop());
        }
        
        // Lưu lại history trừ move cuối
        while (tempHistory.size() > 1) {
            Move m = tempHistory.pop();
            moveHistory.push(m);
            
            Piece playerColor = (moveHistory.size() % 2 == 1) ? Piece.BLACK : Piece.WHITE;
            newBoard.makeMove(m, playerColor);
        }
        
        tempHistory.pop(); // Loại bỏ move cuối (undo)
        
        this.board = newBoard;
        switchPlayer(); // Chuyển ngược lại lượt của player trước
        return true;
    }
    
    // Restart lại trò chơi (reset board và state nhưng giữ players)
    public void restart() {
        this.board = new Board();
        this.currentPlayer = blackPlayer;
        this.isGameOver = false;
        this.moveHistory.clear();
    }

}
