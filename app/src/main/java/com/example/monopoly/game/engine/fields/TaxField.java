package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.NoActionFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;
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

    public TaxField(String label, String message, int i) {
        super(label);
        this.message = message;
        this.taxValue = i;
    }

    @Override
    public void action(GameEngine gameEngine) {
        this.action(gameEngine,new TaxFragment());
    }


}
