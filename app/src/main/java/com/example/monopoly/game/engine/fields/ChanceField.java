package com.example.monopoly.game.engine.fields;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.ChanceFragment;

public class ChanceField extends Field {

    public ChanceField(int id, String label) {
        super(id, label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine, new ChanceFragment());
    }
}