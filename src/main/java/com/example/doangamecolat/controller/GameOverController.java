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

    @FXML private Label winnerLabel;
    @FXML private Label scoreBlackLabel;
    @FXML private Label scoreWhiteLabel;

    // Thêm ID cho ảnh vòng tròn để đổi màu Vàng/Bạc tùy người thắng
    @FXML private ImageView blackRingImg;
    @FXML private ImageView whiteRingImg;

    // Đường dẫn ảnh (Lưu ý check lại đường dẫn thực tế trong project của bạn)
    private final String IMG_GOLD = getClass().getResource("/com/example/doangamecolat/img/ring_gold_glow.png").toExternalForm();
    private final String IMG_SILVER = getClass().getResource("/com/example/doangamecolat/img/ring_silver_plain.png").toExternalForm();

    @FXML
    private void onMenuBtn(ActionEvent event) throws IOException {
        // Quay về Menu chính (Thay đổi tên file fxml nếu cần)
        switchScene(event, "/com/example/doangamecolat/view/menu-view.fxml", "Menu Game");
    }

    @FXML
    private void onPlayAgain(ActionEvent event) throws IOException {
        // Chơi lại -> Load lại bàn cờ
        switchScene(event, "/com/example/doangamecolat/view/game-view.fxml", "Game Cờ Lật");
    }

    // Hàm tiện ích để chuyển cảnh tránh lặp code
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
            // Đen thắng
            winnerLabel.setText("BLACK WINS!");
            winnerLabel.setStyle("-fx-text-fill: #FFD700;"); // Màu vàng

            // Set ảnh: Đen -> Vàng, Trắng -> Bạc
            blackRingImg.setImage(new Image(IMG_GOLD));
            whiteRingImg.setImage(new Image(IMG_SILVER));

        } else if (whiteScore > blackScore) {
            // Trắng thắng
            winnerLabel.setText("WHITE WINS!");
            winnerLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Màu trắng

            // Set ảnh: Đen -> Bạc, Trắng -> Vàng
            blackRingImg.setImage(new Image(IMG_SILVER));
            whiteRingImg.setImage(new Image(IMG_GOLD));

        } else {
            // Hòa
            winnerLabel.setText("DRAW!");
            winnerLabel.setStyle("-fx-text-fill: #ADD8E6;"); // Màu xanh nhạt

            // Cả 2 cùng bạc hoặc cùng vàng tùy bạn
            blackRingImg.setImage(new Image(IMG_SILVER));
            whiteRingImg.setImage(new Image(IMG_SILVER));
        }
    }
}