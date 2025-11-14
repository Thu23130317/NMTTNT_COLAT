package com.example.doangamecolat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {
    @FXML
    private Label winnerLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button playAgainButton;
    @FXML
    private Button menuButton;

    private Stage gameBoardStage;
}