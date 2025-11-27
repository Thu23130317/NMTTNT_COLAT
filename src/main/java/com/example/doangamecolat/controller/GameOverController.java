package com.example.doangamecolat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {
    @FXML
    private void onMenuBtn(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/menu-view.fxml", "Menu Game");
    }

    @FXML
    private void onPlayAgain(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/game-board-view.fxml", "Game Cờ Lật");
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

    }
}