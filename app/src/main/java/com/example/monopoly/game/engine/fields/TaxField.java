package com.example.monopoly.game.engine.fields;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.TaxFragment;

public class TaxField extends Field{

    private final int taxValue;
    private final String message;

    public int getTaxValue() {
        return this.taxValue;
    }
    public String getMessage() {
        return this.message;
    }

    public TaxField(int id, String label, String message, int i) {
        super(id, label);
        this.message = message;
        this.taxValue = i;
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine,new TaxFragment());
    }


}
