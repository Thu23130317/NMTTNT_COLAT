package com.example.doangamecolat.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.doangamecolat.model.AIPlayer;
import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Game;
import com.example.doangamecolat.model.HumanPlayer;
import com.example.doangamecolat.model.Move;
import com.example.doangamecolat.model.Piece;
import com.example.doangamecolat.model.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameBoardController implements Initializable{
    @FXML private GridPane boardGrid;
    @FXML private Label blackScoreLabel;
    @FXML private Label whiteScoreLabel;

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        if (game != null && undoCount < 3 && game.undoLastMove()) {
            undoCount++;
            System.out.println("Undo lần " + undoCount + "/3");
            updateUI();
        } else if (undoCount >= 3) {
            System.out.println("Đã hết lần undo!");
            switchScene(event, "/com/example/doangamecolat/view/menu-view.fxml", "Menu Game");
        } else {
            switchScene(event, "/com/example/doangamecolat/view/menu-view.fxml", "Menu Game");
        }
    }
    @FXML
    private void onRestart(ActionEvent event) throws IOException {
        if (game != null) {
            game.restart();
            hintCount = 0;  // Reset hint counter
            undoCount = 0;  // Reset undo counter
            updateUI();
            processAiTurn();
        }
    }
    
    @FXML
    private void onHint(ActionEvent event) {
        if (game == null || isRunningAi) return;
        
        if (hintCount >= 3) {
            System.out.println("Đã hết lần gợi ý!");
            return;
        }
        
        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            List<Move> validMoves = game.getValidMovesForCurrentPlayer();
            if (validMoves.isEmpty()) {
                System.out.println("Không có nước đi hợp lệ!");
                return;
            }
            
            Move bestMove = com.example.doangamecolat.ai.MiniMax.findBestMove(
                game.getBoard(), 
                game.getCurrentPlayer().getPieceColor(), 
                3
            );
            
            if (bestMove != null) {
                hintCount++;
                highlightHintCell(bestMove.getRow(), bestMove.getCol());
                System.out.println("Gợi ý lần " + hintCount + "/3: Đánh ở (" + bestMove.getRow() + ", " + bestMove.getCol() + ")");
            }
        }
    }
    
    private void highlightHintCell(int row, int col) {
        StackPane cell = getCellPane(row, col);
        if (cell != null) {
            // Tạo dấu chấm đen ở giữa ô
            Circle hintDot = new Circle(5);
            hintDot.setStyle("-fx-fill: black;");
            hintDot.setId("hint-dot");
            cell.getChildren().add(hintDot);
            
            // Tự động xóa hint sau 2 giây
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> {
                        for (var child : cell.getChildren()) {
                            if (child instanceof Circle && child.getId() != null && child.getId().equals("hint-dot")) {
                                cell.getChildren().remove(child);
                                break;
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    private void switchScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    private final double CELL_SIZE = 45.0;
    private Game game;
    private Player blackPlayer;
    private Player whitePlayer;
    private boolean isRunningAi = false;
    private int hintCount = 0; // Đếm số lần dùng gợi ý
    private int undoCount = 0; // Đếm số lần dùng undo

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBoardGrid();
    }

    public void initGame(Player blackPlayer, Player whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.game = new Game(blackPlayer, whitePlayer);

        updateUI();
        processAiTurn();
    }


    private void handleCellClick(int row, int col) {
        // Nếu game kết thúc hoặc AI đang chạy loop thì không nhận click
        if (game.isGameOver() || isRunningAi) return;

        // 1. Người chơi đánh
        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            boolean success = game.playTurn(row, col);
            if (success) {
                updateUI();

                // 2. Sau khi người đánh xong, gọi hàm xử lý AI
                processAiTurn();
            }
        }
    }
    // Hàm xử lý logic AI (chạy trên Main Thread - sẽ làm đông cứng màn hình khi suy nghĩ)
    private void processAiTurn() {
        // Kiểm tra xem có phải lượt AI không
        if (!game.isGameOver() && game.getCurrentPlayer() instanceof AIPlayer) {
            isRunningAi = true; // Bật cờ để chặn click chuột lung tung

            // Dùng vòng lặp while để xử lý trường hợp AI đánh liên tiếp (nếu người bị mất lượt)
            while (!game.isGameOver() && game.getCurrentPlayer() instanceof AIPlayer) {
                System.out.println("AI (" + game.getCurrentPlayer().getPieceColor() + ") đang tính toán...");

                // ⚠️ Dòng này sẽ làm treo giao diện cho đến khi tính xong (do chọn đơn luồng)
                Move aiMove = game.getCurrentPlayer().getMove(game.getBoard());

                if (aiMove != null) {
                    game.playTurn(aiMove.getRow(), aiMove.getCol());
                    System.out.println("AI đánh: " + aiMove);
                } else {
                    System.out.println("AI không có nước đi -> Pass lượt.");
                    game.nextTurn();
                }

                // Cập nhật điểm và bàn cờ sau mỗi nước đi của AI
                updateUI();
            }

            isRunningAi = false; // Tắt cờ, trả lại quyền điều khiển cho người
        }
    }

    private void updateUI() {
        blackScoreLabel.setText(String.valueOf(game.getScore(Piece.BLACK)));
        whiteScoreLabel.setText(String.valueOf(game.getScore(Piece.WHITE)));

        renderBoard();
        highlightValidMoves();

        if (game.isGameOver()) {
            showGameOver();
        }
    }
    
    private void highlightValidMoves() {
        // Xóa tất cả dấu chấm hợp lệ cũ
        for (var node : boardGrid.getChildren()) {
            if (node instanceof StackPane) {
                var children = ((StackPane) node).getChildren();
                children.removeIf(child -> child instanceof Circle && child.getId() != null && child.getId().equals("valid-dot"));
            }
        }
        
        // Thêm dấu chấm xanh cho nước đi hợp lệ
        List<Move> validMoves = game.getValidMovesForCurrentPlayer();
        for (Move move : validMoves) {
            StackPane cell = getCellPane(move.getRow(), move.getCol());
            if (cell != null) {
                Circle validDot = new Circle(4);
                validDot.setFill(Color.LIME); // Xanh lá sáng
                validDot.setId("valid-dot");
                cell.getChildren().add(validDot);
            }
        }
    }
    private void showGameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/game-over-view.fxml"));
            Parent root = loader.load();

            GameOverController controller = loader.getController();
            controller.setScores(game.getScore(Piece.BLACK), game.getScore(Piece.WHITE));

            Stage stage = (Stage) boardGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderBoard() {
        Board board = game.getBoard();
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                StackPane cell = getCellPane(r, c);
                if (cell == null) continue;

                if (cell.getChildren().size() > 1) {
                    cell.getChildren().remove(1);
                }

                Piece p = board.getPiece(r, c);
                if (p != Piece.EMPTY) {
                    Circle piece = new Circle(CELL_SIZE / 2 - 8);
                    piece.getStyleClass().add(p == Piece.BLACK ? "piece-black" : "piece-white");
                    cell.getChildren().add(piece);
                }
            }
        }
    }

//    private void checkAiTurn() {
//        if (game.isGameOver()) return;
//    }



    private void createBoardGrid() {
        boardGrid.getChildren().clear();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Rectangle bg = new Rectangle(CELL_SIZE, CELL_SIZE);
                bg.getStyleClass().add("grid-cell");

                StackPane cellPane = new StackPane(bg);
                final int row = r;
                final int col = c;
                cellPane.setOnMouseClicked(e -> handleCellClick(row, col));

                boardGrid.add(cellPane, c, r);
            }
        }
    }

    private StackPane getCellPane(int r, int c) {
        for (var node : boardGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == r && GridPane.getColumnIndex(node) == c) {
                return (StackPane) node;
            }
        }
        return null;
    }
}