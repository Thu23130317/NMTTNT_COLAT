package com.example.doangamecolat.settings;

import java.util.prefs.Preferences;

/**
 * Quản lý và lưu trữ cài đặt game
 */
public class SettingsManager {
    private static SettingsManager instance;
    private final Preferences prefs;
    
    private static final String SOUND_EFFECTS_KEY = "soundEffects";
    private static final String MUSIC_KEY = "music";
    
    private SettingsManager() {
        prefs = Preferences.userNodeForPackage(SettingsManager.class);
    }
    
    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }
    
    /**
     * Lưu trạng thái bật/tắt hiệu ứng âm thanh
     */
    public void setSoundEffectsEnabled(boolean enabled) {
        prefs.putBoolean(SOUND_EFFECTS_KEY, enabled);
    }
    
    /**
     * Lấy trạng thái hiệu ứng âm thanh
     */
    public boolean isSoundEffectsEnabled() {
        return prefs.getBoolean(SOUND_EFFECTS_KEY, true); // Mặc định: bật
    }
    
    /**
     * Lưu trạng thái bật/tắt nhạc nền
     */
    public void setMusicEnabled(boolean enabled) {
        prefs.putBoolean(MUSIC_KEY, enabled);
    }
    
    /**
     * Lấy trạng thái nhạc nền
     */
    public boolean isMusicEnabled() {
        return prefs.getBoolean(MUSIC_KEY, true); // Mặc định: bật
    }
}
