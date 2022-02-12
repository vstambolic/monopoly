package com.example.monopoly.game.engine.fields;


import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.RailroadFragment;

public class RailroadField extends Field {
    private static final int RENT = 25;
    public static int calculateRent(int cnt) {
        return RENT * (1<<cnt);
    }

    private Player owner = null;

    public RailroadField(int id, String label) {
        super(id, label);
    }
    public boolean hasOwner() {
        return this.owner != null;
    }
    public void setOwner(Player currentPlayer) {
        this.owner = currentPlayer;
    }
    public Player getOwner() {
        return owner;
    }


    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine, new RailroadFragment());
    }
}
