package com.example.monopoly.game.engine.fields;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.NoActionFragment;

public class NoActionField extends Field{

    private String message;
    public NoActionField(String label, String message) {
        super(label);
        this.message = message;
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine, new NoActionFragment());
    }

    public String getMessage() {
        return this.message;
    }
}
