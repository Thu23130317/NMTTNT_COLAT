package com.example.doangamecolat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/menu-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setTitle("Menu Cờ Lật");
        stage.setScene(scene);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(true);
        stage.centerOnScreen();
        
        // Phím F11 để toggle fullscreen
        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("F11")) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
