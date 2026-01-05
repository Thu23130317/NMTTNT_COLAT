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
    private ToggleGroup colorGroup;
    @FXML
    private ToggleButton blackColorButton;
    @FXML
    private ToggleButton whiteColorButton;
    
    @FXML
    public void initialize() {
        // Load cài đặt đã lưu
        SettingsManager settingsManager = SettingsManager.getInstance();
        SoundManager soundManager = SoundManager.getInstance();
        
        // Thiết lập trạng thái toggle từ cài đặt đã lưu
        soundEffectsToggle.setSelected(settingsManager.isSoundEffectsEnabled());
        musicToggle.setSelected(settingsManager.isMusicEnabled());
        
        // Cập nhật icon ban đầu
        updateSoundEffectsIcon();
        updateMusicIcon();
        
        // Áp dụng cài đặt vào SoundManager
        soundManager.setSoundEffectsEnabled(settingsManager.isSoundEffectsEnabled());
        soundManager.setMusicEnabled(settingsManager.isMusicEnabled());
        
        // Listener cho toggle hiệu ứng âm thanh
        soundEffectsToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            settingsManager.setSoundEffectsEnabled(newVal);
            soundManager.setSoundEffectsEnabled(newVal);
            updateSoundEffectsIcon();
            System.out.println("Hiệu ứng âm thanh: " + (newVal ? "BẬT" : "TẮT"));
        });
        
        // Listener cho toggle nhạc nền
        musicToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            settingsManager.setMusicEnabled(newVal);
            soundManager.setMusicEnabled(newVal);
            updateMusicIcon();
            System.out.println("Nhạc nền: " + (newVal ? "BẬT" : "TẮT"));
        });
        
        if (modeGroup != null) {
            // Kiểm tra giá trị ban đầu
            if (pveButton.isSelected()) {
                difficultyBox.setVisible(true);
            } else {
                difficultyBox.setVisible(false);
            }
            
            // Listener khi thay đổi chế độ chơi
            modeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == pveButton) {
                    difficultyBox.setVisible(true);
                } else {
                    difficultyBox.setVisible(false);
                }
            });
        }
    }
    
    private void updateSoundEffectsIcon() {
        if (soundEffectsToggle.isSelected()) {
            soundEffectsToggle.setText("🔊 Hiệu ứng âm thanh");
        } else {
            soundEffectsToggle.setText("🔇 Hiệu ứng âm thanh");
        }
    }
    
    private void updateMusicIcon() {
        if (musicToggle.isSelected()) {
            musicToggle.setText("🎵 Nhạc nền");
        } else {
            musicToggle.setText("🔕 Nhạc nền");
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

            // Chọn màu cờ: Nếu chọn đen -> Bạn là đen, AI là trắng; Nếu chọn trắng -> Bạn là trắng, AI là đen
            if (blackColorButton.isSelected()) {
                blackPlayer = new HumanPlayer(Piece.BLACK);
                whitePlayer = new AIPlayer(Piece.WHITE, maxDepth);
            } else {
                blackPlayer = new AIPlayer(Piece.BLACK, maxDepth);
                whitePlayer = new HumanPlayer(Piece.WHITE);
            }
        } else {
            // PvP mode: chọn màu cho người chơi 1
            if (blackColorButton.isSelected()) {
                blackPlayer = new HumanPlayer(Piece.BLACK);
                whitePlayer = new HumanPlayer(Piece.WHITE);
            } else {
                blackPlayer = new HumanPlayer(Piece.WHITE); // Điều này không đúng logic, nên giữ nguyên
                whitePlayer = new HumanPlayer(Piece.BLACK);
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/game-board-view.fxml"));
        Parent gameBoardRoot = loader.load();

        GameBoardController gameBoardController = loader.getController();

        if (gameBoardController != null) {
            boolean isPvE = pveButton.isSelected();
            boolean playerChosenBlack = blackColorButton.isSelected();
            
            // Truyền mode (PvE/PvP) và color (Đen/Trắng) trước khi initGame
            gameBoardController.setGameMode(isPvE, playerChosenBlack);
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