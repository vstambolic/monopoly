package com.example.monopoly.game;


import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

public class RollTheDiceService extends LifecycleService{

    public static final String INTENT_ACTION_START_AUDIO = "com.example.monopoly.game.START_AUDIO";
    public static final String INTENT_ACTION_STOP_AUDIO = "com.example.monopoly.game.STOP_AUDIO";


    private LifecycleAwareMediaPlayer player = new LifecycleAwareMediaPlayer();
    private LifecycleAwareAccelerometerDetector accelerometerDetector;

    public void startMediaPlayer() {
        this.player.start(this);
    }
    public void stopMediaPlayer() {
        this.player.stop();
    }

    public void startAccelerator(LifecycleAwareAccelerometerDetector.Callback callback) {
        this.accelerometerDetector.start(callback);
    }

    public void stopAccelerator() {
        this.accelerometerDetector.stop();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.getLifecycle().addObserver(player);
        this.accelerometerDetector= new LifecycleAwareAccelerometerDetector(this);
        this.getLifecycle().addObserver(accelerometerDetector);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public class PrimitiveBinder extends Binder {
        public RollTheDiceService getService() {
            return RollTheDiceService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        super.onBind(intent);
        return new PrimitiveBinder();
    }
}
