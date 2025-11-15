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

    @FXML
    public void initialize() {
        pveButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            difficultyBox.setVisible(isSelected);
        });
    }

    @FXML
    private void onStartButtonPress(ActionEvent event) throws IOException {
        Player blackPlayer;
        Player whitePlayer;
        int maxDepth = 4;
        if (easyButton.isSelected()) {
            maxDepth = 2;
        } else if (hardButton.isSelected()) {
            maxDepth = 6;
        }

        // Người chơi là ĐEN, AI là TRẮNG
        blackPlayer = new HumanPlayer(Piece.BLACK);
        whitePlayer = new AIPlayer(Piece.WHITE, maxDepth);
        // TODO: Có thể thêm lựa chọn cho phép người chơi chọn quân TRẮNG


        // 2. Tải màn hình bàn cờ
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/game-board-view.fxml"));
        Parent gameBoardRoot = loader.load();

        // 3. Lấy GameBoardController và truyền dữ liệu người chơi qua
        GameBoardController gameBoardController = loader.getController();
//        gameBoardController.initGame(blackPlayer, whitePlayer); // <-- Phương thức này cần được TẠO MỚI

        // 4. Chuyển Scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(gameBoardRoot));
        stage.setTitle("Game Cờ Lật");
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