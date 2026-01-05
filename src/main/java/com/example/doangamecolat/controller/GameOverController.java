package com.example.doangamecolat.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverController {
    @FXML private Label scoreBlackLabel;
    @FXML private Label scoreWhiteLabel;
    @FXML private Label winnerLabel;
    
    @FXML
    private void onMenuBtn(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/menu-view.fxml", "Menu Game");
    }

    @FXML
    private void onPlayAgain(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/settings-view.fxml", "Game Cờ Lật");
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    public void setScores(int blackScore, int whiteScore) {
        scoreBlackLabel.setText(String.valueOf(blackScore));
        scoreWhiteLabel.setText(String.valueOf(whiteScore));
        
        if (blackScore > whiteScore) {
            winnerLabel.setText("BẠN THẮNG!");
            winnerLabel.setTextFill(javafx.scene.paint.Color.web("#FFD700"));
        } else if (whiteScore > blackScore) {
            winnerLabel.setText("ĐỐI THỦ THẮNG!");
            winnerLabel.setTextFill(javafx.scene.paint.Color.web("#00CED1"));
        } else {
            winnerLabel.setText("HÒA NHAU!");
            winnerLabel.setTextFill(javafx.scene.paint.Color.web("#FFD700"));
        }
    }
}