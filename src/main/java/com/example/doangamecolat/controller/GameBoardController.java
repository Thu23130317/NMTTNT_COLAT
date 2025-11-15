package com.example.doangamecolat.controller;

import com.example.doangamecolat.model.Game;
import com.example.doangamecolat.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class GameBoardController {
    private Game game;

    @FXML
    private Label blackScoreLabel;

    @FXML
    private Label whiteScoreLabel;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private GridPane boardGrid;

    @FXML
    private Button backToMenuButton;

    private final double CELL_SIZE = 70.0;
        /**
     * Vẽ 64 ô vuông của bàn cờ
     */
    private void drawBoard() {
        boardGrid.getChildren().clear(); // Xóa bàn cờ cũ (nếu có)

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Tạo ô vuông
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.web("#008000")); // Màu xanh lá cây đậm
                cell.setStroke(Color.BLACK); // Viền đen

                // StackPane cho phép chúng ta đặt quân cờ lên trên ô
                StackPane cellPane = new StackPane(cell);

                // Thêm ô vào GridPane
                boardGrid.add(cellPane, col, row);
            }
        }
    }

}

