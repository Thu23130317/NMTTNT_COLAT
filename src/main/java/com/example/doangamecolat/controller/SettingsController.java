package com.example.doangamecolat.controller;

import java.io.IOException;

import com.example.doangamecolat.audio.SoundManager;
import com.example.doangamecolat.model.AIPlayer;
import com.example.doangamecolat.model.HumanPlayer;
import com.example.doangamecolat.model.Piece;
import com.example.doangamecolat.model.Player;
import com.example.doangamecolat.settings.SettingsManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Đổi từ RadioButton sang ToggleButton
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private ToggleButton soundEffectsToggle;
    @FXML
    private ToggleButton musicToggle;
    
    @FXML
    public void initialize() {
        // Load cài đặt đã lưu
        SettingsManager settingsManager = SettingsManager.getInstance();
        SoundManager soundManager = SoundManager.getInstance();
        
        // Thiết lập trạng thái toggle từ cài đặt đã lưu
        soundEffectsToggle.setSelected(settingsManager.isSoundEffectsEnabled());
        musicToggle.setSelected(settingsManager.isMusicEnabled());
        
        // Áp dụng cài đặt vào SoundManager
        soundManager.setSoundEffectsEnabled(settingsManager.isSoundEffectsEnabled());
        soundManager.setMusicEnabled(settingsManager.isMusicEnabled());
        
        // Listener cho toggle hiệu ứng âm thanh
        soundEffectsToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            settingsManager.setSoundEffectsEnabled(newVal);
            soundManager.setSoundEffectsEnabled(newVal);
            System.out.println("Hiệu ứng âm thanh: " + (newVal ? "BẬT" : "TẮT"));
        });
        
        // Listener cho toggle nhạc nền
        musicToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            settingsManager.setMusicEnabled(newVal);
            soundManager.setMusicEnabled(newVal);
            System.out.println("Nhạc nền: " + (newVal ? "BẬT" : "TẮT"));
        });
        
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