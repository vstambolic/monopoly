package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.Field;
import com.example.monopoly.game.engine.fields.NoActionField;

import java.util.ArrayList;
import java.util.List;

public abstract class ControllerFragment extends Fragment {

    private static final String FIELD = "FIELD";
    private static final String GAME_STATE = "GAME_STATE";

    protected Field field;
    private GameEngine.GameState gameState;
    protected GameEngine gameEngine;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getParentFragment();
        if (getArguments() != null) {
            this.field = (Field) getArguments().getSerializable(FIELD);
            this.gameState = (GameEngine.GameState) getArguments().getSerializable(GAME_STATE);
        }
    }

    public void init(GameEngine gameEngine, Field field) {
        this.gameState = gameEngine.getGameState();
        this.gameEngine =gameEngine;
        this.field = field;


        Bundle args = new Bundle();
        args.putSerializable(FIELD, field);
        args.putSerializable(GAME_STATE, gameState);
        this.setArguments(args);
    }

    protected void initGameEngine() {
        this.gameEngine = new GameEngine((GameFragment) this.getParentFragment(),((GameFragment)this.getParentFragment()).getMonopoly(),gameState);
    }
}
