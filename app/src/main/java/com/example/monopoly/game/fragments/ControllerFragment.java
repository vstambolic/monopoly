package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.fields.Field;
import com.example.monopoly.game.engine.fields.NoActionField;

public abstract class ControllerFragment extends Fragment {

    private static final String FIELD = "FIELD";
    private static final String GAME_ENGINE = "GAME_ENGINE";


    protected GameEngine gameEngine;
    protected Field field;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.field = (Field) getArguments().getSerializable(FIELD);
            this.gameEngine = (GameEngine) getArguments().getSerializable(GAME_ENGINE);
        }
    }

    public void init(GameEngine gameEngine, Field field) {
        this.gameEngine = gameEngine;
        this.field = field;

        this.gameEngine.getGameFragment().enableNextTurnButton();
        Bundle args = new Bundle();
        args.putSerializable(FIELD, field);
        args.putSerializable(GAME_ENGINE, gameEngine);
        this.setArguments(args);
    }
}
