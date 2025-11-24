package com.example.doangamecolat.controller;

import com.example.doangamecolat.model.Board;
import com.example.doangamecolat.model.Game;
import com.example.doangamecolat.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable; // Cần thêm cái này
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

// Implement Initializable để chạy code ngay khi giao diện hiện lên
public class GameBoardController implements Initializable {

    @FXML private Label blackScoreLabel;
    @FXML private Label whiteScoreLabel;
    @FXML private Label currentPlayerLabel;
    @FXML private GridPane boardGrid;

    // Các button chức năng
    @FXML private Button pauseButton;
    @FXML private Button restartButton;
    @FXML private Button undoButton;
    @FXML private Button hintButton;

    private final double CELL_SIZE = 45.0; // Kích thước này phải khớp với FXML

    @FXML
    private GridPane gridPane; // Lưới bàn cờ (liên kết từ FXML)

    @FXML
    private Label scoreBlackLabel; // Nhãn điểm Đen

    @FXML
    private Label scoreWhiteLabel; // Nhãn điểm Trắng

    @FXML
    private Label turnLabel; // Nhãn báo lượt đi hiện tại

    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private Board board;

    // --- PHƯƠNG THỨC KHỞI TẠO GAME (Được gọi từ SettingsController) ---
    public void initGame(Player blackPlayer, Player whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;

        // Khởi tạo bàn cờ logic mới
        this.board = new Board();

        // Quy tắc Cờ Lật: Quân ĐEN luôn đi trước
        this.currentPlayer = this.blackPlayer;

        // Cập nhật giao diện lần đầu
        updateUI();

        // Nếu người chơi đầu tiên là AI (trường hợp mở rộng), cần kích hoạt AI đi ngay
        checkAIKhaiCuoc();
    }

    // Hàm cập nhật giao diện (Vẽ lại bàn cờ, cập nhật điểm)
    private void updateUI() {
        // 1. Cập nhật điểm số
//        int blackScore = board.countPieces(Piece.BLACK);
//        int whiteScore = board.countPieces(Piece.WHITE);

//        if (scoreBlackLabel != null) scoreBlackLabel.setText("Đen: " + blackScore);
//        if (scoreWhiteLabel != null) scoreWhiteLabel.setText("Trắng: " + whiteScore);

        // 2. Cập nhật thông báo lượt đi
        if (turnLabel != null) {
//            String luotDi = (currentPlayer.getDisc() == Piece.BLACK) ? "Lượt ĐEN đi" : "Lượt TRẮNG đi";
//            turnLabel.setText(luotDi);
        }

        // 3. Vẽ lại các quân cờ trên GridPane
        renderBoard();
    }

    // Hàm vẽ bàn cờ (Giả định bạn đã có logic vẽ hình ảnh quân cờ)
    private void renderBoard() {
        // Xóa các quân cờ cũ trên giao diện (nếu cần)
        // Duyệt qua mảng board[8][8] và thêm ImageView vào gridPane
        // (Phần này phụ thuộc vào cách bạn xử lý hình ảnh trong project)
        System.out.println("Đang vẽ lại bàn cờ...");
    }

    // Kiểm tra nếu lượt hiện tại là AI thì gọi AI tính nước đi
    private void checkAIKhaiCuoc() {
//        if (currentPlayer.isAI()) {
            // Gọi hàm xử lý nước đi của AI (thường chạy trong Thread khác để không đơ giao diện)
            System.out.println("AI đang suy nghĩ...");
            // makeAIMove();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Vẽ lưới bàn cờ ngay khi mở
        createBoardGrid();

        // 2. (Test) Thử đặt vài quân cờ mẫu để xem CSS hoạt động chưa
        // Khi có logic Game thật, bạn sẽ gọi hàm updateBoard từ Model
        placePiece(3, 3, true);  // Trắng
        placePiece(3, 4, false); // Đen
        placePiece(4, 3, false); // Đen
        placePiece(4, 4, true);  // Trắng
    }

    /**
     * Tạo lưới 8x8 (Chỉ chạy 1 lần đầu game)
     */
    private void createBoardGrid() {
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Tạo ô vuông trong suốt để hiện nền gỗ bên dưới
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.getStyleClass().add("grid-cell"); // Dùng CSS .grid-cell thay vì setFill màu cứng

                StackPane cellPane = new StackPane(cell);

                // Lưu tọa độ vào cellPane để xử lý click
                final int r = row;
                final int c = col;
                cellPane.setOnMouseClicked(event -> handleCellClick(r, c));

                boardGrid.add(cellPane, col, row);
            }
        }
    }

    /**
     * Xử lý khi người chơi bấm vào ô (row, col)
     */
    private void handleCellClick(int row, int col) {
        System.out.println("Đã bấm vào ô: " + row + ", " + col);
        // TODO: Gọi logic game kiểm tra nước đi hợp lệ tại đây
        // Ví dụ: if (game.isValidMove(row, col)) { ... }

        // Test thử: Bấm vào đâu đặt quân đen vào đó
        placePiece(row, col, false);
    }

    /**
     * Đặt quân cờ lên bàn
     * @param isWhite: true là Trắng, false là Đen
     */
    private void placePiece(int row, int col, boolean isWhite) {
        // Tìm ô StackPane tại vị trí row, col
        StackPane cellPane = getCellPane(row, col);
        if (cellPane == null) return;

        // Xóa quân cũ nếu có (giữ lại cái background Rectangle ở index 0)
        if (cellPane.getChildren().size() > 1) {
            cellPane.getChildren().remove(1);
        }

        // Tạo quân cờ hình tròn
        Circle piece = new Circle(CELL_SIZE / 2 - 5); // Trừ đi padding

        // Áp dụng CSS 3D siêu thực bạn đã viết
        if (isWhite) {
            piece.getStyleClass().add("piece-white");
        } else {
            piece.getStyleClass().add("piece-black");
        }

        cellPane.getChildren().add(piece);
    }

    // Helper để lấy StackPane từ GridPane (vì GridPane không hỗ trợ get theo index trực tiếp tốt)
    private StackPane getCellPane(int row, int col) {
        for (var node : boardGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (StackPane) node;
            }
        }
        return null;
    }
}