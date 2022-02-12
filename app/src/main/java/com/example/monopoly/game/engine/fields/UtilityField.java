package com.example.monopoly.game.engine.fields;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.UtilityFragment;

public class UtilityField extends Field {

    private Player owner = null;

    public Player getOwner() {
        return owner;
    }

    public UtilityField(int id, String label) {
        super(id, label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine,new UtilityFragment());
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

    public void setOwner(Player currentPlayer) {
        this.owner = currentPlayer;
    }

}