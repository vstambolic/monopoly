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

    public TaxField(String label, String message, int i) {
        super(label);
        this.message = message;
        this.taxValue = i;
    }

    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        TaxFragment taxFragment = (TaxFragment)fragmentManager.findFragmentByTag(TaxFragment.TAX_FRAGMENT_TAG);

        taxFragment.bindField(this);
        taxFragment.setGameEngine(gameEngine);

        fragmentManager
                .beginTransaction()
                .hide(rollTheDiceFragment)
                .show(taxFragment)
                .commit();
    }

    public int getTaxValue() {
        return this.taxValue;
    }

    public String getMessage() {
        return this.message;
    }

    public String getFieldLabel() {
        return this.label;
    }
}
