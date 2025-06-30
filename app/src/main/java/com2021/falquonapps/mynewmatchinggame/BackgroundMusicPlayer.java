package com2021.falquonapps.mynewmatchinggame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class BackgroundMusicPlayer {

    private static final String PREF_NAME = "BackgroundMusicPrefs";
    private static final String KEY_BACKGROUND_MUSIC_ENABLED = "BackgroundMusicEnabled";

    private Context context;
    private MediaPlayer mediaPlayer;
    private SharedPreferences prefs;
    private int currentPosition;

    public BackgroundMusicPlayer(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void start() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentPosition = mediaPlayer.getCurrentPosition();
        }
    }

    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }
    }

    public boolean isMusicEnabled() {
        return prefs.getBoolean(KEY_BACKGROUND_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_BACKGROUND_MUSIC_ENABLED, enabled).apply();
        if (enabled) {
            start();
        } else {
            stop();
        }
    }
}

