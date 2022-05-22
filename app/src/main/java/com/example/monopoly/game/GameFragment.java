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

import com.example.monopoly.R;
import com.example.monopoly.data.Game;
import com.example.monopoly.data.GameRepository;
import com.example.monopoly.data.GameStateSnapshot;
import com.example.monopoly.data.GameStateSnapshotRepository;
import com.example.monopoly.data.MonopolyDatabase;
import com.example.monopoly.databinding.FragmentGameBinding;
import com.example.monopoly.game.custom_views.Monopoly;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.Field;
import com.example.monopoly.game.fragments.RollTheDiceFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GameFragment extends Fragment {

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


    private GameEngine gameEngine;
    private int dice1val = 5;
    private int dice2val = 3;
    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    private GameRepository gameRepo;
    private GameStateSnapshotRepository gameStateSnapshotRepo;


    // Dice roll -----------------------------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        this.getChildFragmentManager()
                .beginTransaction()
                .add(R.id.controller_frame, new RollTheDiceFragment(this), RollTheDiceFragment.ROLL_THE_DICE_TAG)
                .commit();

        // INIT DB ---------------------------------------------------------------------------------
        MonopolyDatabase monopolyDatabase = MonopolyDatabase.getInstance(requireContext());
        gameRepo = new GameRepository(monopolyDatabase.gameDao());
        gameStateSnapshotRepo = new GameStateSnapshotRepository(monopolyDatabase.gameStateSnapshotDao());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);

        this.gameViewModel.initByInstanceStateBundle(savedInstanceState);

        GameEngine.GameState gameState;
        if (this.gameViewModel.getGameState() != null) {
            gameState = this.gameViewModel.getGameState();
        }
        else {
            Field.init();
            gameState = new GameEngine.GameState(this.getPlayers());
            this.gameViewModel.setGameState(gameState);
            this.gameViewModel.setCurrGameStateIndex(0);


            ArrayList<String> playerNames = new ArrayList<>();
            for (Player p : this.getPlayers()) {
                playerNames.add(p.getName());
            }
            gameRepo.getInsertedId().observe(getViewLifecycleOwner(), gameId -> {
                if (gameId != -1) {
                    this.gameViewModel.setGameId(gameId);
                    this.insertGameStateSnapshot();
                }
            });
            Game game = new Game(0, new Date(),0,playerNames,0);
            gameRepo.insert(game);
        }
        this.gameEngine = new GameEngine(this, binding.monopoly, gameState);

        setCurrentPlayer();
        
        // Settings button -------------------------------------------------------------------------
        binding.settingsButton.setOnClickListener(v -> {
            Navigation.findNavController(
                    getActivity(),
                    R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_gameFragment_to_settingsFragment);
        });

        // Quit button -----------------------------------------------------------------------------
        binding.exitButton.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Are you sure you want to quit?")
                    .setNegativeButton("no", null)
                    .setPositiveButton("yes", (dialog, which) -> {
                        GameFragment.this.deleteGameFromDatabase();
                        Navigation.findNavController(
                                getActivity(),
                                R.id.nav_host_fragment_content_main)
                                .navigate(R.id.action_gameFragment_to_WelcomeFragment);
                    })
                    .show();
        });

        View.OnClickListener onClickListener = v -> NavHostFragment
                .findNavController(GameFragment.this)
                .navigate(GameFragmentDirections
                        .actionGameFragmentToPlayerInfoFragment(GameFragment.this.gameEngine.getCurrentPlayer()));

        binding.currPlayerBalanceButton.setOnClickListener(onClickListener);
        binding.currPlayerNameButton.setOnClickListener(onClickListener);

        binding.doneButton.setOnClickListener(v -> {
            gameEngine.nextTurn();
            this.setCurrentPlayer();

            if (this.gameEngine.isGameOver())
                this.gameOver();
            else
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.controller_frame, new RollTheDiceFragment(this), RollTheDiceFragment.ROLL_THE_DICE_TAG)
                        .commit();
        });
        this.initDiceRoll();
        return binding.getRoot();
    }

    public void deleteGameFromDatabase() {
        gameRepo.delete(this.gameViewModel.getGameId());
    }

    private void gameOver() {
        // todo update game in database
        NavHostFragment
                .findNavController(GameFragment.this)
                .navigate(GameFragmentDirections
                        .actionGameFragmentToGameOverFragment(GameFragment.this.gameEngine.getCurrentPlayer()));

    }

    // Initialization ------------------------------------------------------------------------------

    private void setCurrentPlayer() {
        Player p = this.gameEngine.getCurrentPlayer();
        this.binding.currPlayerNameButton.setText(p.getName() + "'s turn");
        this.binding.currPlayerNameButton.setBackgroundColor(Constants.PLAYER_COLORS[p.getId()]);

        this.binding.currPlayerBalanceButton.setText("$" + p.getBalance());
        this.binding.currPlayerBalanceButton.setBackgroundColor(Constants.PLAYER_COLORS[p.getId()]);

        this.binding.doneButton.setEnabled(false);
    }

    private List<Player> getPlayers() {
        String[] playerNames = GameFragmentArgs.fromBundle(requireArguments()).getPlayers();
        List<Player> players = new ArrayList<>(playerNames.length);
        for (int i = 0; i < playerNames.length; i++) {
            players.add(new Player(playerNames[i], i));
        }
        return players;
    }

    private void initDiceRoll() {
        binding.dice1.setImageResource(R.drawable.dice_5);
        binding.dice2.setImageResource(R.drawable.dice_3);
    }
    public void setDiceValue() {
        binding.textviewDiceVal.setText(String.valueOf(dice1val + dice2val));
    }
    // ---------------------------------------------------------------------------------------------

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
        int rand = (int) (Math.random() * 6) + 1;
        iv.setImageResource(this.getImageResource(rand));
        return rand;
    }

    private int getImageResource(int index) {
        switch (index) {
            case 1:
                return (R.drawable.dice_1);
            case 2:
                return (R.drawable.dice_2);
            case 3:
                return (R.drawable.dice_3);
            case 4:
                return (R.drawable.dice_4);
            case 5:
                return (R.drawable.dice_5);
            default:
                return (R.drawable.dice_6);
        }
    }

    private void movePlayer() {
        this.gameEngine.moveCurrentPlayer(this.dice1val+this.dice2val);
    }

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
        outState.putSerializable(
                GameViewModel.GAME_ID,
                this.gameViewModel.getGameId());
        outState.putSerializable(
                GameViewModel.CURRENT_GAME_STATE_INDEX,
                this.gameViewModel.getCurrGameStateIndex());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.gameEngine.stopPlayerMoving();
    }



    public void enableNextTurnButton() {
        this.binding.doneButton.setEnabled(true);
    }

    public void setPlayerBalance(int balance) {
        this.binding.currPlayerBalanceButton.setText("$" + balance);
    }

    public int getDiceVal() {
        return this.dice1val + dice2val;
    }

    public GameEngine getGameEngine() {
        return this.gameEngine;
    }

    public Monopoly getMonopoly() {
        return this.binding.monopoly;
    }


    public void insertGameStateSnapshot() {
        long gameId = this.gameViewModel.getGameId();
        long index = this.gameViewModel.getCurrGameStateIndex();
        GameEngine.GameState gameState = this.gameViewModel.getGameState();
        GameStateSnapshot snapshot = new GameStateSnapshot(gameId, index, gameState);
        this.gameStateSnapshotRepo.insert(snapshot);

        this.gameViewModel.setCurrGameStateIndex(index + 1);
    }

}