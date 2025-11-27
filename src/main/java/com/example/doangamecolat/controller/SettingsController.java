package com.example.doangamecolat.controller;

import com.example.doangamecolat.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton; // Đổi từ RadioButton sang ToggleButton
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private ToggleGroup modeGroup; // Cần khai báo trong FXML
    @FXML
    private ToggleButton pvpButton; // Cần thêm nút này trong FXML
    @FXML
    private ToggleButton pveButton;

    @FXML
    private VBox difficultyBox;
    @FXML
    private ToggleGroup difficultyGroup;

    @FXML
    private ToggleButton easyButton;   // Đổi thành ToggleButton
    @FXML
    private ToggleButton mediumButton; // Đổi thành ToggleButton
    @FXML
    private ToggleButton hardButton;   // Đổi thành ToggleButton

    @FXML
    public void initialize() {
        // Logic: Nếu chọn PvE thì hiện hộp chọn độ khó, nếu PvP thì ẩn đi
        if (modeGroup != null) {
            modeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == pveButton) {
                    difficultyBox.setVisible(true);
                } else {
                    difficultyBox.setVisible(false);
                }
            });
        }
    }

    @FXML
    private void onStartButtonPress(ActionEvent event) throws IOException {
        Player blackPlayer;
        Player whitePlayer;

        // 1. Kiểm tra chế độ chơi
        if (pveButton.isSelected()) {
            // --- CHẾ ĐỘ PVE ---
            int maxDepth = 4; // Mặc định là Thường
            if (easyButton.isSelected()) maxDepth = 2;
            else if (hardButton.isSelected()) maxDepth = 6;

            blackPlayer = new HumanPlayer(Piece.BLACK);
            whitePlayer = new AIPlayer(Piece.WHITE, maxDepth);
        } else {
            // --- CHẾ ĐỘ PVP (Người đấu Người) ---
            blackPlayer = new HumanPlayer(Piece.BLACK);
            whitePlayer = new HumanPlayer(Piece.WHITE);
        }

        // 2. Tải màn hình bàn cờ
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/game-board-view.fxml"));
        Parent gameBoardRoot = loader.load();

        // 3. Truyền dữ liệu sang GameBoardController
        GameBoardController gameBoardController = loader.getController();

        // QUAN TRỌNG: Bạn cần bỏ comment dòng này và đảm bảo bên GameBoardController đã có hàm initGame
        if (gameBoardController != null) {
            gameBoardController.initGame(blackPlayer, whitePlayer);
        }

        // 4. Chuyển Scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(gameBoardRoot));
        stage.setTitle("Game Cờ Lật - Đang chơi");
        stage.centerOnScreen();
    }

    @FXML
    private void onBackBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/menu-view.fxml"));
        Parent menuRoot = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(menuRoot));
        stage.setTitle("Menu Cờ Lật");
    }
}