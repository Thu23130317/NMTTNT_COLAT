package com.example.doangamecolat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class MenuController {
    @FXML
    private JFXButton playButton;

    @FXML
    private void onPlayButtonPress(ActionEvent event) throws IOException {
        // Tải màn hình Cài đặt (settings-view.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/settings-view.fxml"));
        Parent settingsRoot = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(settingsRoot));
        stage.setTitle("Cài đặt Game");
    }
}
