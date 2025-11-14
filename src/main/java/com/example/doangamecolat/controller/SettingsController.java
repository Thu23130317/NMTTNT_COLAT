package com.example.doangamecolat.controller;

import com.example.doangamecolat.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private ToggleGroup modeGroup;
    @FXML
    private RadioButton pvpButton;
    @FXML
    private RadioButton pveButton;

    @FXML
    private VBox difficultyBox;
    @FXML
    private ToggleGroup difficultyGroup;
    @FXML
    private RadioButton easyButton;
    @FXML
    private RadioButton mediumButton;
    @FXML
    private RadioButton hardButton;

    /**
     * Được gọi tự động khi FXML được tải
     */
    @FXML
    public void initialize() {
        // Thêm listener để ẩn/hiện mục chọn độ khó
        pveButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            difficultyBox.setVisible(isSelected);
        });
    }

    /**
     * Xử lý khi nhấn nút "Bắt đầu"
     */
    @FXML
    private void onStartButtonPress(ActionEvent event) throws IOException {
        Player blackPlayer;
        Player whitePlayer;

        // 1. Xác định người chơi dựa trên lựa chọn
        if (pvpButton.isSelected()) {
            // Chế độ Người vs Người
            blackPlayer = new HumanPlayer(Piece.BLACK);
            whitePlayer = new HumanPlayer(Piece.WHITE);
        } else {
            // Chế độ Người vs Máy
            int maxDepth = 4; // Mặc định là trung bình
            if (easyButton.isSelected()) {
                maxDepth = 2;
            } else if (hardButton.isSelected()) {
                maxDepth = 6;
            }

            // Người chơi là ĐEN, AI là TRẮNG
            blackPlayer = new HumanPlayer(Piece.BLACK);
            whitePlayer = new AIPlayer(Piece.WHITE, maxDepth);
            // TODO: Có thể thêm lựa chọn cho phép người chơi chọn quân TRẮNG
        }

        // 2. Tải màn hình bàn cờ
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/game-board-view.fxml"));
        Parent gameBoardRoot = loader.load();

        // 3. Lấy GameBoardController và truyền dữ liệu người chơi qua
        GameBoardController gameBoardController = loader.getController();
//        gameBoardController.initGame(blackPlayer, whitePlayer); // <-- Phương thức này cần được TẠO MỚI

        // 4. Chuyển Scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(gameBoardRoot));
        stage.setTitle("Game Cờ Lật");
    }

    /**
     * Xử lý khi nhấn nút "Quay lại Menu"
     */
    @FXML
    private void onBackButtonPress(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/menu-view.fxml"));
        Parent menuRoot = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(menuRoot));
        stage.setTitle("Menu Cờ Lật");
    }
}