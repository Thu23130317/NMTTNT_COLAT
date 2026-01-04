package com.example.doangamecolat.animation;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class PieceAnimation {
    
    /**
     * Hiệu ứng lật quân cờ với đổi màu (dành cho Othello/Reversi)
     * @param piece Node cần animation (Circle)
     * @param newStyleClass Class CSS màu mới
     * @param oldStyleClass Class CSS màu cũ cần xóa
     */
    public static void flipWithColorChange(Node piece, String newStyleClass, String oldStyleClass) {
        // Xoay quanh trục Y - tăng thời gian lên 800ms
        RotateTransition rotate = new RotateTransition(Duration.millis(800), piece);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(180);
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        
        // Scale để tạo hiệu ứng lật 3D - tăng thời gian lên 400ms
        ScaleTransition scale = new ScaleTransition(Duration.millis(400), piece);
        scale.setFromX(1.0);
        scale.setToX(0.1);
        scale.setFromY(1.0);
        scale.setToY(1.1);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        
        // Đổi màu ở giữa animation (khi xoay 90 độ - mặt quân cờ không nhìn thấy)
        rotate.setOnFinished(null);
        scale.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            // Khi đến giữa animation (50% - quân cờ xoay ngang) - điều chỉnh timing
            if (newTime.toMillis() >= 200 && oldTime.toMillis() < 200) {
                piece.getStyleClass().remove(oldStyleClass);
                piece.getStyleClass().add(newStyleClass);
            }
        });
        
        ParallelTransition parallel = new ParallelTransition(rotate, scale);
        parallel.play();
    }
    
    /**
     * Hiệu ứng lật xoay 3D khi quân cờ đổi màu
     * @param piece Node cần animation (Circle)
     * @param onFinish Callback khi animation kết thúc
     */
    public static void flipRotateAnimation(Node piece, Runnable onFinish) {
        // Xoay quanh trục Y (lật ngang)
        RotateTransition rotate = new RotateTransition(Duration.millis(400), piece);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(180);
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        
        // Thêm hiệu ứng scale nhẹ để tạo cảm giác lật
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), piece);
        scale.setFromX(1.0);
        scale.setToX(0.3);
        scale.setFromY(1.0);
        scale.setToY(1.0);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        
        ParallelTransition parallel = new ParallelTransition(rotate, scale);
        
        if (onFinish != null) {
            parallel.setOnFinished(e -> onFinish.run());
        }
        
        parallel.play();
    }
    
    /**
     * Hiệu ứng phóng to rồi thu nhỏ khi quân cờ đổi màu
     */
    public static void scaleAnimation(Node piece, Runnable onFinish) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), piece);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.3);
        scale.setToY(1.3);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        
        if (onFinish != null) {
            scale.setOnFinished(e -> onFinish.run());
        }
        
        scale.play();
    }
    
    /**
     * Hiệu ứng mờ dần rồi hiện lại với màu mới
     */
    public static void fadeAnimation(Node piece, Runnable onFinish) {
        FadeTransition fade = new FadeTransition(Duration.millis(200), piece);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setAutoReverse(true);
        fade.setCycleCount(2);
        
        if (onFinish != null) {
            fade.setOnFinished(e -> onFinish.run());
        }
        
        fade.play();
    }
    
    /**
     * Hiệu ứng kết hợp: xoay + phóng to + mờ (hiệu ứng đẹp nhất)
     */
    public static void combinedFlipAnimation(Node piece, Runnable onFinish) {
        // Xoay
        RotateTransition rotate = new RotateTransition(Duration.millis(500), piece);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(180);
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        
        // Scale
        ScaleTransition scale = new ScaleTransition(Duration.millis(250), piece);
        scale.setFromX(1.0);
        scale.setToX(0.2);
        scale.setFromY(1.0);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        
        // Fade nhẹ
        FadeTransition fade = new FadeTransition(Duration.millis(250), piece);
        fade.setFromValue(1.0);
        fade.setToValue(0.3);
        fade.setAutoReverse(true);
        fade.setCycleCount(2);
        
        ParallelTransition parallel = new ParallelTransition(rotate, scale, fade);
        
        if (onFinish != null) {
            parallel.setOnFinished(e -> onFinish.run());
        }
        
        parallel.play();
    }
    
    /**
     * Hiệu ứng bounce khi đặt quân cờ mới
     */
    public static void placeAnimation(Node piece) {
        piece.setScaleX(0.0);
        piece.setScaleY(0.0);
        
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), piece);
        scale.setFromX(0.0);
        scale.setFromY(0.0);
        scale.setToX(1.2);
        scale.setToY(1.2);
        
        ScaleTransition bounce = new ScaleTransition(Duration.millis(100), piece);
        bounce.setFromX(1.2);
        bounce.setFromY(1.2);
        bounce.setToX(1.0);
        bounce.setToY(1.0);
        
        SequentialTransition seq = new SequentialTransition(scale, bounce);
        seq.play();
    }
    
    /**
     * Hiệu ứng pulse khi highlight nước đi hợp lệ
     */
    public static void pulseAnimation(Node node) {
        ScaleTransition pulse = new ScaleTransition(Duration.millis(600), node);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.4);
        pulse.setToY(1.4);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setInterpolator(Interpolator.EASE_BOTH);
        pulse.play();
    }
}
