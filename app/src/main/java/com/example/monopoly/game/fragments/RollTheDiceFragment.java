package com.example.monopoly.game.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.monopoly.databinding.FragmentRollTheDiceBinding;
import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.roll_the_dice.LifecycleAwareAccelerometerDetector;
import com.example.monopoly.game.roll_the_dice.RollTheDiceService;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RollTheDiceFragment extends Fragment {
    public static final String ROLL_THE_DICE_TAG = "ROLL_THE_DICE_TAG";



    private FragmentRollTheDiceBinding binding;

    private GameFragment gameFragment;
    public RollTheDiceFragment(GameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentRollTheDiceBinding.inflate(inflater, container, false);
        this.binding.rollTheDiceButton.setOnClickListener(v->{
            if (this.isBound())
                this.prepareRolling();
        });
        getActivity().bindService(new Intent(getActivity(),RollTheDiceService.class), serviceConnection, Context.BIND_AUTO_CREATE);

        return this.binding.getRoot();
    }

    private boolean startedRolling = false;
    private Future<?> future;
    private Future<?> stoppedFuture = null;

    public void prepareRolling() {
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
        binding.rollTheDiceButton.setEnabled(false);
        this.gameFragment.prepareRolling();
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
                handler.post(() -> RollTheDiceFragment.this.gameFragment.setDiceValue());
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    return;
                }
                handler.post(() -> RollTheDiceFragment.this.gameFragment.stopRolling());
            });
        }
    }

    // Roll The Dice ----------------------------------------------------------
    public void rollTheDice() {
        this.gameFragment.rollTheDice();
    }

    public boolean isBound() {
        return this.rollTheDiceService !=null;
    }
    private RollTheDiceService rollTheDiceService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            rollTheDiceService = ((RollTheDiceService.PrimitiveBinder)service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            rollTheDiceService = null;
        }
    };

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
}