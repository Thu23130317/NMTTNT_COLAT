package com.example.doangamecolat.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class HowToPlayController implements Initializable {
    @FXML
    private TextFlow rulesTextFlow;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRules();
    }
    
    private void loadRules() {
        if (rulesTextFlow != null) {
            String rulesText = "CỜ LẬT (OTHELLO)\n\n" +
                    "MỤC ĐÍCH TRÒ CHƠI:\n" +
                    "Có nhiều quân cờ cùng màu hơn đối thủ khi trò chơi kết thúc.\n\n" +
                    
                    "LUẬT CHƠI CƠ BẢN:\n" +
                    "1. Bàn cờ 8×8, bắt đầu với 4 quân ở giữa (2 đen, 2 trắng).\n" +
                    "2. Người chơi đen đi trước.\n" +
                    "3. Mỗi nước đi phải 'lật' ít nhất 1 quân của đối thủ.\n" +
                    "4. Lật: Đặt quân của bạn sao cho có ít nhất 1 quân đối thủ nằm giữa quân mới và quân cũ của bạn (theo một hướng).\n\n" +
                    
                    "CÁC HƯỚNG LẬT:\n" +
                    "Quân có thể lật theo 8 hướng: ngang, dọc, và 2 đường chéo.\n\n" +
                    
                    "QUY TẮC BỎ NƯỚC:\n" +
                    "- Nếu không có nước đi hợp lệ, bạn phải bỏ lượt.\n" +
                    "- Nếu cả 2 người chơi đều không có nước đi, trò chơi kết thúc.\n\n" +
                    
                    "TÍNH ĐIỂM:\n" +
                    "Khi trò chơi kết thúc, người chơi có quân nhiều hơn sẽ thắng.\n\n" +
                    
                    "CHIẾN LƯỢC:\n" +
                    "- Góc bàn cờ rất quan trọng (khó lật lại).\n" +
                    "- Tránh để đối thủ lật nhiều quân trong một nước.\n" +
                    "- Cố gắng kiểm soát trung tâm bàn cờ.\n" +
                    "- Phòng thủ bằng cách giới hạn lựa chọn của đối thủ.";
            
            Text text = new Text(rulesText);
            text.getStyleClass().add("rules-text");
            rulesTextFlow.getChildren().add(text);
        }
    }
    
    @FXML
    private void onBackBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/doangamecolat/view/menu-view.fxml"));
        Parent menuRoot = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(menuRoot));
        stage.setTitle("Menu Cờ Lật");
    }
}
