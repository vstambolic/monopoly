package com.example.monopoly.game;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentGameBinding;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.ChanceFragment;
import com.example.monopoly.game.fragments.GoToJailFragment;
import com.example.monopoly.game.fragments.NoActionFragment;
import com.example.monopoly.game.fragments.PropertyFragment;
import com.example.monopoly.game.fragments.RailroadFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;
import com.example.monopoly.game.fragments.TaxFragment;
import com.example.monopoly.game.fragments.UtilityFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class GameFragment extends Fragment {

    private FragmentManager fragmentManager;
    private RollTheDiceFragment rollTheDiceFragment;
    private GameEngine gameEngine;
    private NoActionFragment noActionFragment;
    private GoToJailFragment goToJailFragment;
    private TaxFragment taxFragment;
    private PropertyFragment propertyFragment;
    private RailroadFragment railroadFragment;
    private UtilityFragment utilityFragment;
    private ChanceFragment chanceFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fragmentManager = this.getChildFragmentManager();
        if (fragmentManager.getFragments().size() == 0) {
            this.taxFragment = new TaxFragment();
            this.rollTheDiceFragment = new RollTheDiceFragment(this);
            this.noActionFragment = NoActionFragment.newInstance("","");
            this.goToJailFragment = new GoToJailFragment();
            this.propertyFragment = new PropertyFragment();
            this.railroadFragment = new RailroadFragment();
            this.utilityFragment = new UtilityFragment();
            this.chanceFragment = new ChanceFragment();
        }
        fragmentManager
                .beginTransaction()
                .add(R.id.controller_frame, this.chanceFragment,ChanceFragment.CHANCE_FRAGMENT_TAG)
                .hide(chanceFragment)
                .add(R.id.controller_frame, this.utilityFragment, UtilityFragment.UTILITY_FRAGMENT_TAG)
                .hide(utilityFragment)
                .add(R.id.controller_frame, this.railroadFragment, RailroadFragment.RAILROAD_FRAGMENT_TAG)
                .hide(railroadFragment)
                .add(R.id.controller_frame, this.propertyFragment, PropertyFragment.PROPERTY_FRAGMENT_TAG)
                .hide(propertyFragment)
                .add(R.id.controller_frame, this.taxFragment, TaxFragment.TAX_FRAGMENT_TAG)
                .hide(taxFragment)
                .add(R.id.controller_frame, this.goToJailFragment, GoToJailFragment.GO_TO_JAIL_FRAGMENT_TAG)
                .hide(goToJailFragment)
                .add(R.id.controller_frame, this.noActionFragment, NoActionFragment.NO_ACTION_FRAGMENT_TAG )
                .hide(noActionFragment)
                .add(R.id.controller_frame, this.rollTheDiceFragment, RollTheDiceFragment.ROLL_THE_DICE_TAG)
                .show(rollTheDiceFragment)
                .commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);
        this.initGame();

        // Settings button -------------------------------------------------------------------------
        binding.settingsButton.setOnClickListener(v->{
          Navigation.findNavController(
                  getActivity(),
                  R.id.nav_host_fragment_content_main)
                  .navigate(R.id.action_gameFragment_to_settingsFragment);
        });

        // Quit button -----------------------------------------------------------------------------
        binding.exitButton.setOnClickListener(v->{
            new AlertDialog.Builder(getActivity())
                    .setTitle("Are you sure you want to quit?")
                    .setNegativeButton("no",null)
                    .setPositiveButton("yes", (dialog, which) -> {
                        Navigation.findNavController(
                                getActivity(),
                                R.id.nav_host_fragment_content_main)
                                .navigate(R.id.action_gameFragment_to_WelcomeFragment);
                    })
                    .show();
        });

        this.initDiceRoll();
        return binding.getRoot();
    }

    // Initialization ------------------------------------------------------------------------------
    private void initGame() {
        this.initGameEngine();
        this.setCurrentPlayer();
    }

    private void initGameEngine() {
        this.gameEngine = new GameEngine(this, this.binding.monopoly, this.getPlayers());
    }

    private void setCurrentPlayer() {
        Player p = this.gameEngine.getCurrentPlayer();
        this.binding.currPlayerNameButton.setText(p.getName() + "'s turn");
        this.binding.currPlayerNameButton.setBackgroundColor(Constants.PLAYER_COLORS[p.getId()]);
    }


    private List<Player> getPlayers() {
        String[] playerNames = GameFragmentArgs.fromBundle(requireArguments()).getPlayers();
        List<Player> players = new ArrayList<>(playerNames.length);
        for (int i = 0; i < playerNames.length; i++) {
            players.add(new Player(playerNames[i],i));
        }
        return players;
    }


    // Dice roll -----------------------------------------------------------------------------------

    private int dice1val = 5;
    private int dice2val = 3;


    private void initDiceRoll() {
        binding.dice1.setImageResource(R.drawable.dice_5);
        binding.dice2.setImageResource(R.drawable.dice_3);
    }

    public void setDiceValue() {
        binding.textviewDiceVal.setText(String.valueOf(dice1val+dice2val));
        // TODO what to do with dis
    }

    public void prepareRolling() {
        binding.diceWrapper.setVisibility(View.VISIBLE);
    }

    public void stopRolling() {
        binding.diceWrapper.setVisibility(View.INVISIBLE);
        binding.textviewDiceVal.setText("");
        this.movePlayer();
    }


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
    // ---------------------------------------------------------------------------------------------

    private void movePlayer() {
        this.gameEngine.moveCurrentPlayer(2);
//        this.gameEngine.moveCurrentPlayer(this.dice1val+this.dice2val);
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

    public void enableNextTurnButton() {
    }

    public void setPlayerBalance(int balance) {
        // todo
    }

    public int getDiceVal() {
        return this.dice1val + dice2val;
    }
}