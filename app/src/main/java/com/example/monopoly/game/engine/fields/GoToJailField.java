package com.example.monopoly.game.engine.fields;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.GoToJailFragment;
public class GoToJailField extends Field {
    public GoToJailField(int id, String label) {
        super(id,label);
    }
    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine, new GoToJailFragment());
    }
}
