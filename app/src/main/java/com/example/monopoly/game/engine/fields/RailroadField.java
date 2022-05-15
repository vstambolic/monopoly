package com.example.monopoly.game.engine.fields;


import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.RailroadFragment;

public class RailroadField extends OwnableField {
    private static final int RENT = 25;
    public static int calculateRent(int cnt) {
        return RENT * (1<<cnt);
    }

    public RailroadField(int id, String label) {
        super(id, label);
    }
    private static final String DESCRIPTION = "--------------------------------------------\nRENT: $25\nIf 2 railroads owned: $50\nIf 3 railroads owned: $100\nIf 4 railroads owned: $200\n--------------------------------------------\n";

    @Override
    public String getDescription() {
        return DESCRIPTION + (this.isMortgaged()? "\n*mortgaged*":"");
    }

    @Override
    public int calculateMortgage() {
        return 100;
    }

    @Override
    public int calculateLiftMortgage() {
        return 120;
    }



    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine, new RailroadFragment());
    }
}
