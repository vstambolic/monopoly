package com.example.monopoly.game;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.io.IOException;


public class LifecycleAwareMediaPlayer implements DefaultLifecycleObserver {

    private MediaPlayer mediaPlayer = null;

    public LifecycleAwareMediaPlayer() { }

    public void start(Context context) {
        Log.wtf("STARTED","MEDIA PLAYER");
        if (mediaPlayer == null) {
            try {
                String song = "dice.mp3";
                String path = context.getFilesDir().getAbsolutePath() + File.separator + song;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
       this.stop();
    }
}
