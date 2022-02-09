package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.ChanceFragment;
import com.example.monopoly.game.fragments.NoActionFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;
import com.example.monopoly.game.fragments.UtilityFragment;

public class ChanceField extends Field {

    public ChanceField(String label) {
        super(label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine, new ChanceFragment());
    }
}