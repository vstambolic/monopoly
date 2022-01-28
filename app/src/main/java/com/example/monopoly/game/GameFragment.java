package com.example.monopoly.game;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentGameBinding;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class GameFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);
        this.getPlayers();

        getActivity().bindService(new Intent(getActivity(),RollTheDiceService.class), serviceConnection, Context.BIND_AUTO_CREATE);

        binding.buttonRollTheDice.setOnClickListener(view ->{
            if (this.isBound())
                GameFragment.this.prepareRolling();
        });

        binding.dice1.setImageResource(R.drawable.dice_5);
        binding.dice2.setImageResource(R.drawable.dice_3);

        return binding.getRoot();
    }


    private boolean startedRolling = false;
    private Future<?> future;
    private Future<?> stoppedFuture = null;


    private void prepareRolling() {
        this.rollTheDiceService.startAccelerator(new LifecycleAwareAccelerometerDetector.Callback() {
            @Override
            public void startCallback() {
                startRolling();
            }
            @Override
            public void endCallback() {
                stopRolling();
            }
        });
        binding.buttonRollTheDice.setEnabled(false);
        binding.diceWrapper.setVisibility(View.VISIBLE);
    }

    private void startRolling() {
        startedRolling = true;
        Handler handler = new Handler(Looper.getMainLooper());
        this.rollTheDiceService.startMediaPlayer();
        this.future = Executors.newSingleThreadExecutor().submit(()->{
            while (true) {
                SystemClock.sleep(200);
                if (Thread.interrupted()) {
                    return;
                }
                handler.post(()->rollTheDice());
            }
        });
    }

    public void stopRolling() {
        if (startedRolling) {
            this.future.cancel(true);
            this.rollTheDiceService.stopMediaPlayer();

            stoppedFuture = Executors.newSingleThreadExecutor().submit(() -> {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(()->binding.textviewDiceVal.setText(String.valueOf(dice1val+dice2val)));
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    return;
                }
                Log.d("", dice1val+ " "+ dice2val);
                handler.post(() -> {
                    binding.diceWrapper.setVisibility(View.INVISIBLE);
                    binding.textviewDiceVal.setText("");
                });
            });
        }
    }


    // Roll The Dice ----------------------------------------------------------
    private int dice1val = 5;
    private int dice2val = 3;
    public void rollTheDice() {
        this.dice1val = this.rollTheDice(binding.dice1);
        this.dice2val = this.rollTheDice(binding.dice2);
    }
    private int rollTheDice(ImageView iv) {
        int rand = (int)(Math.random()*6) + 1;
        iv.setImageResource(this.getImageResource(rand));
        return rand;
    }
    private int getImageResource(int index) {
        switch (index) {
            case 1:return (R.drawable.dice_1);
            case 2:return (R.drawable.dice_2);
            case 3:return (R.drawable.dice_3);
            case 4:return (R.drawable.dice_4);
            case 5:return (R.drawable.dice_5);
            default:return (R.drawable.dice_6);
        }
    }


    private boolean isBound() {
        return this.rollTheDiceService !=null;
    }
    private RollTheDiceService rollTheDiceService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GameFragment.this.rollTheDiceService = ((RollTheDiceService.PrimitiveBinder)service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            GameFragment.this.rollTheDiceService = null;
        }
    };





    private void getPlayers() {
        String[] players = GameFragmentArgs.fromBundle(requireArguments()).getPlayers();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (startedRolling) {
            startedRolling = false;

            if (stoppedFuture !=null) {
                stoppedFuture.cancel(true);
            }
            this.rollTheDiceService.stopAccelerator();
            this.rollTheDiceService.stopMediaPlayer();
        }

        getActivity().unbindService(serviceConnection);
//        binding = null;
    }


    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = () -> {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        Activity activity = getActivity();
        if (activity != null
                && activity.getWindow() != null) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    };


    private final Runnable mHideRunnable = () -> hide();
    private FragmentGameBinding binding;


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        delayedHide(100);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
        }
        show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void hide() {
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mHideHandler.removeCallbacks(mHidePart2Runnable);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }




}