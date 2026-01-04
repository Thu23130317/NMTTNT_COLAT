package com.example.doangamecolat.audio;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Quản lý âm thanh trong game
 */
public class SoundManager {
    private static SoundManager instance;
    
    private MediaPlayer backgroundMusic;
    private MediaPlayer placePieceSound;

    
    private boolean soundEffectsEnabled = true;
    private boolean musicEnabled = true;
    
    private SoundManager() {
        loadSounds();
    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    private void loadSounds() {
        try {
            // Âm thanh đặt quân cờ
            URL placeSoundUrl = getClass().getResource("/com/example/doangamecolat/audio/dat-co.mp3");
            if (placeSoundUrl != null) {
                Media placeMedia = new Media(placeSoundUrl.toString());
                placePieceSound = new MediaPlayer(placeMedia);
                placePieceSound.setVolume(0.6);
            }


            
            // Nhạc nền
            URL musicUrl = getClass().getResource("/com/example/doangamecolat/audio/background-music.mp3");
            if (musicUrl != null) {
                Media musicMedia = new Media(musicUrl.toString());
                backgroundMusic = new MediaPlayer(musicMedia);
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
                backgroundMusic.setVolume(0.3);
            }
            
        } catch (Exception e) {
            System.err.println("Không thể load âm thanh: " + e.getMessage());
        }
    }
    
    /**
     * Phát âm thanh khi đặt quân cờ
     */
    public void playPlacePieceSound() {
        if (soundEffectsEnabled && placePieceSound != null) {
            placePieceSound.stop();
            placePieceSound.seek(Duration.ZERO);
            placePieceSound.play();
        }
    }
    
    /**
     * Phát âm thanh khi lật quân cờ (tạo MediaPlayer mới để nhiều âm thanh phát cùng lúc)
     */
    public void playFlipPieceSound() {
        if (soundEffectsEnabled) {
            try {
                URL flipSoundUrl = getClass().getResource("/com/example/doangamecolat/audio/lat-co.mp3");
                if (flipSoundUrl != null) {
                    Media flipMedia = new Media(flipSoundUrl.toString());
                    MediaPlayer player = new MediaPlayer(flipMedia);
                    player.setVolume(0.6);
                    
                    // Tự động dispose sau khi phát xong
                    player.setOnEndOfMedia(() -> player.dispose());
                    
                    player.play();
                }
            } catch (Exception e) {
                // Bỏ qua lỗi để không ảnh hưởng game
            }
        }
    }
    
    /**
     * Bật nhạc nền
     */
    public void playBackgroundMusic() {
        if (musicEnabled && backgroundMusic != null) {
            backgroundMusic.play();
        }
    }
    
    /**
     * Dừng nhạc nền
     */
    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    
    /**
     * Tạm dừng nhạc nền
     */
    public void pauseBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }
    
    /**
     * Bật/tắt hiệu ứng âm thanh
     */
    public void setSoundEffectsEnabled(boolean enabled) {
        this.soundEffectsEnabled = enabled;
    }
    
    /**
     * Bật/tắt nhạc nền
     */
    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (!enabled && backgroundMusic != null) {
            backgroundMusic.pause();
        } else if (enabled && backgroundMusic != null) {
            backgroundMusic.play();
        }
    }
    
    public boolean isSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }
    
    public boolean isMusicEnabled() {
        return musicEnabled;
    }
    
    /**
     * Dọn dẹp tài nguyên
     */
    public void cleanup() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        if (placePieceSound != null) {
            placePieceSound.dispose();
        }
    }
}
