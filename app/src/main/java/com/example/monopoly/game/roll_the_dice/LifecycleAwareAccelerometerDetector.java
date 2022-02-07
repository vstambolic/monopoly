package com.example.monopoly.game.roll_the_dice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class LifecycleAwareAccelerometerDetector implements DefaultLifecycleObserver {

    private static final float START_THRESHOLD = 3f; // TODO Configure in settings
    private static final float STOP_THRESHOLD = 0.5f; // TODO Configure in settings
    private static final boolean VIBRATE = false; // TODO Configure in settings

    private boolean firstTime = true;
    private boolean startCallBackCalled = false;

    private float prevX;
    private float prevY;
    private float prevZ;
    private float currX;
    private float currY;
    private float currZ;

    private Vibrator vibrator;


    public interface Callback {
        void startCallback();
        void endCallback();
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
    }

    private SensorManager sensorManager = null;
    private final Sensor sensor;

    public LifecycleAwareAccelerometerDetector(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (VIBRATE)
        this.vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

    }


    private Callback callback;
    public void start(Callback callback) {
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.callback =callback;
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.stop();
    }

    private final SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            currX = event.values[0];
            currY = event.values[1];
            currZ = event.values[2];

            if (!LifecycleAwareAccelerometerDetector.this.firstTime) {
                float dx = Math.abs(currX-prevX);
                float dy = Math.abs(currY-prevY);
                float dz = Math.abs(currZ-prevZ);

                if ((dx> START_THRESHOLD && dy > START_THRESHOLD) ||(dx> START_THRESHOLD && dz > START_THRESHOLD) ||(dz> START_THRESHOLD && dy > START_THRESHOLD)) {
                    if (!startCallBackCalled) {
                        startCallBackCalled = true;
                        LifecycleAwareAccelerometerDetector.this.callback.startCallback();
                    }
                    if (VIBRATE)
                        LifecycleAwareAccelerometerDetector.this.vibrate();
                }
                else
                    if (startCallBackCalled) {
                        if ((dx < STOP_THRESHOLD && dy < STOP_THRESHOLD) ||(dx< STOP_THRESHOLD && dz < STOP_THRESHOLD) ||(dz< STOP_THRESHOLD && dy < STOP_THRESHOLD)) {
                            LifecycleAwareAccelerometerDetector.this.callback.endCallback();
                            LifecycleAwareAccelerometerDetector.this.stop();
                        }
                    }
            }
            else
                LifecycleAwareAccelerometerDetector.this.firstTime = false;

            prevX=currX;
            prevY=currY;
            prevZ=currZ;
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
           this.vibrator.vibrate(VibrationEffect.createOneShot(400,VibrationEffect.DEFAULT_AMPLITUDE));
        else
            this.vibrator.vibrate(400);
    }
}
