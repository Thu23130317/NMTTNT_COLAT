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
            winnerLabel.setText("BLACK WINS!");
            winnerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-effect: dropshadow(one-pass-box, black, 3, 0.5, 0, 0);");
            winnerLabel.setTextFill(javafx.scene.paint.Color.web("#FFD700"));
        } else if (whiteScore > blackScore) {
            winnerLabel.setText("WHITE WINS!");
            winnerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-effect: dropshadow(one-pass-box, black, 3, 0.5, 0, 0);");
            winnerLabel.setTextFill(javafx.scene.paint.Color.web("#E0E0E0"));
        } else {
            winnerLabel.setText("IT'S A DRAW!");
            winnerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-effect: dropshadow(one-pass-box, black, 3, 0.5, 0, 0);");
            winnerLabel.setTextFill(javafx.scene.paint.Color.web("#FFD700"));
        }
    }
}