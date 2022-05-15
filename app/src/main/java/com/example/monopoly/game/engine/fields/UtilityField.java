package com.example.monopoly.game.engine.fields;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.UtilityFragment;

public class UtilityField extends OwnableField {
    private static final String DESCRIPTION = "--------------------------------------------\nIf 1 utility owned: 4 times dice value.\nIf 2 utilities owned: 10 times dice value.\n--------------------------------------------\n";

    public UtilityField(int id, String label) {
        super(id, label);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION + (this.isMortgaged()? "\n*mortgaged*":"");
    }

    @Override
    public int calculateMortgage() {
        return 75;
    }

    @Override
    public int calculateLiftMortgage() {
        return 90;
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine,new UtilityFragment());
    }

}