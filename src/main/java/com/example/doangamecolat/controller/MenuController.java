package com.example.doangamecolat.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private void onPlayBtn(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/settings-view.fxml", "Game Cờ Lật");
    }

    @FXML
    private void onSettingsBtn(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/settings-view.fxml", "Cài đặt Game");
    }

    @FXML
    private void onExitBtn(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}