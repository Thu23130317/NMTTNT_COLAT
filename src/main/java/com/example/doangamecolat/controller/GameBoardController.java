package com.example.doangamecolat.controller;

import com.example.doangamecolat.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable{
    @FXML private GridPane boardGrid;
    @FXML private Label blackScoreLabel;
    @FXML private Label whiteScoreLabel;
    @FXML private Label currentPlayerLabel;

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/menu-view.fxml", "Menu Game");
    }
    @FXML
    private void onRestart(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/doangamecolat/view/game-board-view.fxml", "Chơi Game");
    }
    private void switchScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    private final double CELL_SIZE = 45.0;
    private Game game;
    private Player blackPlayer;
    private Player whitePlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBoardGrid();
    }

    public void initGame(Player blackPlayer, Player whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.game = new Game(blackPlayer, whitePlayer);

        updateUI();
        checkAIKhaiCuoc();
    }

    private void handleCellClick(int row, int col) {
        if (game.isGameOver() || (game.getCurrentPlayer() instanceof AIPlayer)) {
            return;
        }
        boolean success = game.playTurn(row, col);
        if (success) {
            updateUI();
            checkAIKhaiCuoc();
        } else {
            System.out.println("Nước đi không hợp lệ!");
        }
    }

    private void updateUI() {
        blackScoreLabel.setText(String.valueOf(game.getScore(Piece.BLACK)));
        whiteScoreLabel.setText(String.valueOf(game.getScore(Piece.WHITE)));

        renderBoard();

        if (game.isGameOver()) {
            showGameOver();
        }
    }
    private void showGameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/game-over-view.fxml"));
            Parent root = loader.load();

            GameOverController controller = loader.getController();
            controller.setScores(game.getScore(Piece.BLACK), game.getScore(Piece.WHITE));

            Stage stage = (Stage) boardGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderBoard() {
        Board board = game.getBoard();
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                StackPane cell = getCellPane(r, c);
                if (cell == null) continue;

                if (cell.getChildren().size() > 1) {
                    cell.getChildren().remove(1);
                }

                Piece p = board.getPiece(r, c);
                if (p != Piece.EMPTY) {
                    Circle piece = new Circle(CELL_SIZE / 2 - 8);
                    piece.getStyleClass().add(p == Piece.BLACK ? "piece-black" : "piece-white");
                    cell.getChildren().add(piece);
                }
            }
        }
    }

    private void checkAIKhaiCuoc() {
            System.out.println("AI đang suy nghĩ...");
    }

    private void createBoardGrid() {
        boardGrid.getChildren().clear();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Rectangle bg = new Rectangle(CELL_SIZE, CELL_SIZE);
                bg.getStyleClass().add("grid-cell");

                StackPane cellPane = new StackPane(bg);
                final int row = r;
                final int col = c;
                cellPane.setOnMouseClicked(e -> handleCellClick(row, col));

                boardGrid.add(cellPane, c, r);
            }
        }
    }

    private StackPane getCellPane(int r, int c) {
        for (var node : boardGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == r && GridPane.getColumnIndex(node) == c) {
                return (StackPane) node;
            }
        }
        return null;
    }
}