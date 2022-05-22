package com.example.monopoly.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.GameOverFragmentArgs;
import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentGameBinding;
import com.example.monopoly.databinding.FragmentGameSimulationBinding;
import com.example.monopoly.game.custom_views.Monopoly;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.Field;
import com.example.monopoly.game.fragments.RollTheDiceFragment;
import com.example.monopoly.game.simulation.GameSimulationPlayerView;

import java.util.ArrayList;
import java.util.List;


public class GameSimulationFragment extends Fragment {

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

    private FragmentGameSimulationBinding binding;
    private GameViewModel gameViewModel;


    private int gameId;
    // Dice roll -----------------------------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gameId = GameSimulationFragmentArgs.fromBundle(requireArguments()).getGameId();
        this.gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentGameSimulationBinding.inflate(inflater, container, false);

        this.gameViewModel.initByInstanceStateBundle(savedInstanceState);

        GameEngine.GameState gameState;
        if (this.gameViewModel.getGameState() != null) {
            gameState = this.gameViewModel.getGameState();
        }
        else {
            gameState = this.getInitialGameState();
            this.gameViewModel.setGameState(gameState);
        }
        for (Player p : gameState.players) {
            this.binding.playersContainer.addView(new GameSimulationPlayerView(this.requireContext(),this, p));
        }

        // Quit button -----------------------------------------------------------------------------
        binding.exitButton.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Are you sure you want to quit?")
                    .setNegativeButton("no", null)
                    .setPositiveButton("yes", (dialog, which) -> {
                        Navigation.findNavController(
                                getActivity(),
                                R.id.nav_host_fragment_content_main).popBackStack();
                    })
                    .show();
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO ge next turn from database
            }
        });

        return binding.getRoot();
    }

    private GameEngine.GameState getInitialGameState() {
        // todo get initial gamestate setup using gameId (from database)

        List<Player> list = new ArrayList<>(4);
        list.add(new Player("vasilije", 0));
        list.add(new Player("vasilije", 1));
        list.add(new Player("vasilije", 2));
        list.add(new Player("vasilije", 3));
        return new GameEngine.GameState(list);
    }


    // Initialization ------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------


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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(
                GameViewModel.GAME_STATE,
                this.gameViewModel.getGameState());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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