package com.example.doangamecolat.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.doangamecolat.animation.PieceAnimation;
import com.example.doangamecolat.audio.SoundManager;
import com.example.doangamecolat.model.AIPlayer;
import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Game;
import com.example.doangamecolat.model.HumanPlayer;
import com.example.doangamecolat.model.Move;
import com.example.doangamecolat.model.Piece;
import com.example.doangamecolat.model.Player;
import com.example.doangamecolat.settings.SettingsManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameBoardController implements Initializable{
    @FXML private GridPane boardGrid;
    @FXML private Label blackScoreLabel;
    @FXML private Label whiteScoreLabel;
    @FXML private Label turnIndicatorLabel;
    @FXML private Label blackPlayerNameLabel;
    @FXML private Label whitePlayerNameLabel;

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
            undoCount = 0;  // Reset undo counter
            updateUI();
            processAiTurn();
        }
    }
    
    @FXML
    private void onSettingsButton(ActionEvent event) {
        showSettingsDialog();
    }
    
    private void showSettingsDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cài đặt");
        
        // Tạo nội dung chính
        VBox content = new VBox(0);
        content.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(42, 42, 58, 0.95), rgba(30, 30, 50, 0.95)); " +
                        "-fx-border-color: linear-gradient(to right, #00CED1, #DAA520); " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12;");
        
        // Tạo header/menu bar
        VBox headerBox = new VBox();
        headerBox.setPadding(new Insets(12, 20, 12, 20));
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, rgba(0, 206, 209, 0.6), rgba(218, 165, 32, 0.4)); " +
                          "-fx-border-color: linear-gradient(to right, #00CED1, #DAA520); " +
                          "-fx-border-width: 0 0 2 0;");
        
        Label titleLabel = new Label("Âm thanh");
        titleLabel.setStyle("-fx-text-fill: #00CED1; -fx-font-size: 16px; -fx-font-weight: bold;");
        headerBox.getChildren().add(titleLabel);
        
        // Tạo content box với padding
        VBox contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));
        
        SettingsManager settingsManager = SettingsManager.getInstance();
        SoundManager soundManager = SoundManager.getInstance();
        
        // Toggle hiệu ứng âm thanh
        ToggleButton soundEffectsToggle = new ToggleButton("🔊 Hiệu ứng âm thanh");
        soundEffectsToggle.setSelected(settingsManager.isSoundEffectsEnabled());
        soundEffectsToggle.setPrefWidth(220);
        soundEffectsToggle.setStyle("-fx-font-size: 14px; " +
                                   "-fx-padding: 10px; " +
                                   "-fx-background-color: linear-gradient(to bottom, rgba(0, 206, 209, 0.6), rgba(0, 206, 209, 0.3)); " +
                                   "-fx-text-fill: white; " +
                                   "-fx-border-color: #00CED1; " +
                                   "-fx-border-width: 2px; " +
                                   "-fx-border-radius: 8; " +
                                   "-fx-background-radius: 8;");
        
        // Update initial text
        if (settingsManager.isSoundEffectsEnabled()) {
            soundEffectsToggle.setText("🔊 Hiệu ứng âm thanh");
        } else {
            soundEffectsToggle.setText("🔇 Hiệu ứng âm thanh");
        }
        
        soundEffectsToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            settingsManager.setSoundEffectsEnabled(newVal);
            soundManager.setSoundEffectsEnabled(newVal);
            soundEffectsToggle.setText(newVal ? "🔊 Hiệu ứng âm thanh" : "🔇 Hiệu ứng âm thanh");
        });
        
        // Toggle nhạc nền
        ToggleButton musicToggle = new ToggleButton("🎵 Nhạc nền");
        musicToggle.setSelected(settingsManager.isMusicEnabled());
        musicToggle.setPrefWidth(220);
        musicToggle.setStyle("-fx-font-size: 14px; " +
                            "-fx-padding: 10px; " +
                            "-fx-background-color: linear-gradient(to bottom, rgba(0, 206, 209, 0.6), rgba(0, 206, 209, 0.3)); " +
                            "-fx-text-fill: white; " +
                            "-fx-border-color: #00CED1; " +
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 8; " +
                            "-fx-background-radius: 8;");
        
        // Update initial text
        if (settingsManager.isMusicEnabled()) {
            musicToggle.setText("🎵 Nhạc nền");
        } else {
            musicToggle.setText("🔕 Nhạc nền");
        }
        
        musicToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            settingsManager.setMusicEnabled(newVal);
            soundManager.setMusicEnabled(newVal);
            musicToggle.setText(newVal ? "🎵 Nhạc nền" : "🔕 Nhạc nền");
        });
        
        contentBox.getChildren().addAll(soundEffectsToggle, musicToggle);
        
        content.getChildren().addAll(headerBox, contentBox);
        
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setStyle("-fx-background-color: rgba(42, 42, 58, 0.95);");
        
        // Style các nút button
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        // Tìm và style nút Close
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setStyle(
            "-fx-background-color: linear-gradient(to bottom, rgba(218, 165, 32, 0.7), rgba(218, 165, 32, 0.4)); " +
            "-fx-text-fill: white; " +
            "-fx-border-color: #DAA520; " +
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 6; " +
            "-fx-background-radius: 6; " +
            "-fx-padding: 8px 20px; " +
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold;"
        );
        
        dialog.showAndWait();
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
    private int undoCount = 0; // Đếm số lần dùng undo
    private Map<String, Piece> previousBoardState = new HashMap<>(); // Lưu trạng thái board trước đó
    private boolean isPvEMode = false; // false=PvP, true=PvE
    private boolean playerChosenBlack = true; // true=Quân Đen, false=Quân Trắng

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBoardGrid();
    }

    public void initGame(Player blackPlayer, Player whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.game = new Game(blackPlayer, whitePlayer);
        
        // Cập nhật tên người chơi
        updatePlayerNames();

        updateUI();
        processAiTurn();
    }
    
    // Setter để nhận mode (PvE/PvP) và color (Đen/Trắng) từ SettingsController
    public void setGameMode(boolean isPvE, boolean playerChosenBlack) {
        this.isPvEMode = isPvE;
        this.playerChosenBlack = playerChosenBlack;
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
        
        // Cập nhật chỉ báo lượt đi
        updateTurnIndicator();

        renderBoard();
        highlightValidMoves();

        if (game.isGameOver()) {
            showGameOver();
        }
    }
    
    private void updatePlayerNames() {
        boolean isPvP = (blackPlayer instanceof HumanPlayer) && (whitePlayer instanceof HumanPlayer);
        
        if (isPvP) {
            // PvP: dựa vào color được chọn
            if (playerChosenBlack) {
                // Chọn Quân Đen: Bạn là Đen, Đối thủ là Trắng
                if (blackPlayerNameLabel != null) {
                    blackPlayerNameLabel.setText("ĐEN (BẠN):");
                }
                if (whitePlayerNameLabel != null) {
                    whitePlayerNameLabel.setText("TRẮNG (ĐỐI THỦ):");
                }
            } else {
                // Chọn Quân Trắng: Đối thủ là Đen, Bạn là Trắng
                if (blackPlayerNameLabel != null) {
                    blackPlayerNameLabel.setText("ĐEN (ĐỐI THỦ):");
                }
                if (whitePlayerNameLabel != null) {
                    whitePlayerNameLabel.setText("TRẮNG (BẠN):");
                }
            }
        } else {
            // PvE: dựa vào color được chọn
            if (playerChosenBlack) {
                // Chọn Quân Đen: Người chơi là Đen, Máy là Trắng
                if (blackPlayerNameLabel != null) {
                    blackPlayerNameLabel.setText("ĐEN (Người chơi):");
                }
                if (whitePlayerNameLabel != null) {
                    whitePlayerNameLabel.setText("TRẮNG (Máy):");
                }
            } else {
                // Chọn Quân Trắng: Máy là Đen, Người chơi là Trắng
                if (blackPlayerNameLabel != null) {
                    blackPlayerNameLabel.setText("ĐEN (Máy):");
                }
                if (whitePlayerNameLabel != null) {
                    whitePlayerNameLabel.setText("TRẮNG (Người chơi):");
                }
            }
        }
    }
    
    private void updateTurnIndicator() {
        if (turnIndicatorLabel == null) return;
        
        if (game.isGameOver()) {
            turnIndicatorLabel.setText("KẾT THÚC");
            return;
        }
        
        Player currentPlayer = game.getCurrentPlayer();
        Piece currentColor = currentPlayer.getPieceColor();
        
        if (currentPlayer instanceof HumanPlayer) {
            // Lượt người chơi
            if (currentColor == Piece.BLACK) {
                turnIndicatorLabel.setText("ĐEN (BẠN)");
            } else {
                turnIndicatorLabel.setText("TRẮNG (BẠN)");
            }
        } else {
            // Lượt máy
            if (currentColor == Piece.BLACK) {
                turnIndicatorLabel.setText("ĐEN (MÁY)");
            } else {
                turnIndicatorLabel.setText("TRẮNG (MÁY)");
            }
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
        
        // Không hiển thị nước đi hợp lệ nếu game đã kết thúc
        if (game.isGameOver()) {
            return;
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
        boolean hasNewPiece = false;
        int flipCount = 0;
        
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                StackPane cell = getCellPane(r, c);
                if (cell == null) continue;

                String key = r + "," + c;
                Piece previousPiece = previousBoardState.get(key);
                Piece currentPiece = board.getPiece(r, c);

                // Kiểm tra nếu có quân mới được đặt
                if (currentPiece != Piece.EMPTY && (previousPiece == null || previousPiece == Piece.EMPTY)) {
                    hasNewPiece = true;
                }
                
                // Đếm số quân bị lật
                if (previousPiece != null && previousPiece != Piece.EMPTY && previousPiece != currentPiece && currentPiece != Piece.EMPTY) {
                    flipCount++;
                }
            }
        }
        
        // Phát âm thanh đặt cờ TRƯỚC (nếu có)
        if (hasNewPiece) {
            SoundManager.getInstance().playPlacePieceSound();
        }
        
        // Render lại board và phát âm thanh lật
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                StackPane cell = getCellPane(r, c);
                if (cell == null) continue;

                String key = r + "," + c;
                Piece previousPiece = previousBoardState.get(key);
                Piece currentPiece = board.getPiece(r, c);

                // Kiểm tra nếu quân cờ bị lật (đổi màu)
                if (previousPiece != null && previousPiece != Piece.EMPTY && previousPiece != currentPiece && currentPiece != Piece.EMPTY) {
                    // Quân cờ bị lật - giữ quân cũ và chạy animation
                    if (cell.getChildren().size() > 1) {
                        Circle oldPiece = (Circle) cell.getChildren().get(1);
                        
                        // Chạy animation lật và đổi màu giữa chừng
                        PieceAnimation.flipWithColorChange(oldPiece, 
                            currentPiece == Piece.BLACK ? "piece-black" : "piece-white",
                            currentPiece == Piece.BLACK ? "piece-white" : "piece-black"
                        );
                    }
                } else if (currentPiece != Piece.EMPTY && (previousPiece == null || previousPiece == Piece.EMPTY)) {
                    // Quân cờ mới được đặt
                    if (cell.getChildren().size() > 1) {
                        cell.getChildren().remove(1);
                    }
                    Circle piece = new Circle(CELL_SIZE / 2 - 8);
                    piece.getStyleClass().add(currentPiece == Piece.BLACK ? "piece-black" : "piece-white");
                    cell.getChildren().add(piece);
                    
                    PieceAnimation.placeAnimation(piece);
                } else if (currentPiece != Piece.EMPTY && previousPiece == currentPiece) {
                    // Quân cờ không đổi - không làm gì
                    if (cell.getChildren().size() == 1) {
                        // Trường hợp khởi tạo lại board
                        Circle piece = new Circle(CELL_SIZE / 2 - 8);
                        piece.getStyleClass().add(currentPiece == Piece.BLACK ? "piece-black" : "piece-white");
                        cell.getChildren().add(piece);
                    }
                } else if (currentPiece == Piece.EMPTY && previousPiece != Piece.EMPTY) {
                    // Ô trống - xóa quân cờ
                    if (cell.getChildren().size() > 1) {
                        cell.getChildren().remove(1);
                    }
                }

                // Cập nhật trạng thái
                previousBoardState.put(key, currentPiece);
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