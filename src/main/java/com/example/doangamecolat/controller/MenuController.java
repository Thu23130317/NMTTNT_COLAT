package com.example.doangamecolat.controller;

import com.jfoenix.controls.JFXButton;
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

    // Khai báo đúng tên fx:id bên FXML (nếu cần thao tác code với nút)
    @FXML
    private JFXButton btnStartGame;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnExit;

    @FXML
    private void onPlayBtn(ActionEvent event) throws IOException {
        // Logic chuyển sang màn hình chơi game (Ví dụ: game-view.fxml)
        // Hiện tại tôi để tạm settings như cũ, nhưng bạn nên đổi thành file game
        switchScene(event, "/com/example/doangamecolat/view/settings-view.fxml", "Game Cờ Lật");
    }

    @FXML
    private void onSettingsBtn(ActionEvent event) throws IOException {
        // Logic chuyển sang màn hình cài đặt
        switchScene(event, "/com/example/doangamecolat/view/settings-view.fxml", "Cài đặt Game");
    }

    @FXML
    private void onExitBtn(ActionEvent event) {
        // Thoát ứng dụng hoàn toàn
        Platform.exit();
        System.exit(0);
    }

    // Hàm phụ để chuyển cảnh cho gọn code
    private void switchScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}