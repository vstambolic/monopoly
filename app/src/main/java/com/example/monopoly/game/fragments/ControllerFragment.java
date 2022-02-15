package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.fields.Field;

public abstract class ControllerFragment extends Fragment {
    private static final String FIELD = "FIELD";

    protected Field field;
    protected GameEngine gameEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.field = (Field) getArguments().getSerializable(FIELD);
        }
    }

    public void init(Field field) {
        this.field = field;
        Bundle args = new Bundle();
        args.putSerializable(FIELD, field);
        this.setArguments(args);
    }

    protected void initView() {
        this.gameEngine = ((GameFragment)this.getParentFragment()).getGameEngine();
    }

}
