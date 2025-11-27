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
    private ToggleGroup modeGroup;
    @FXML
    private ToggleButton pveButton;
    @FXML
    private VBox difficultyBox;
    @FXML
    private ToggleButton easyButton;
    @FXML
    private ToggleButton mediumButton;
    @FXML
    private ToggleButton hardButton;
    @FXML
    public void initialize() {
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

        if (pveButton.isSelected()) {
            int maxDepth = 4;
            if (easyButton.isSelected()) maxDepth = 2;
            else if (hardButton.isSelected()) maxDepth = 6;

            blackPlayer = new HumanPlayer(Piece.BLACK);
            whitePlayer = new AIPlayer(Piece.WHITE, maxDepth);
        } else {
            blackPlayer = new HumanPlayer(Piece.BLACK);
            whitePlayer = new HumanPlayer(Piece.WHITE);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/game-board-view.fxml"));
        Parent gameBoardRoot = loader.load();

        GameBoardController gameBoardController = loader.getController();

        if (gameBoardController != null) {
            gameBoardController.initGame(blackPlayer, whitePlayer);
        }

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