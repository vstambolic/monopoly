package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.RollTheDiceFragment;
import com.example.monopoly.game.fragments.UtilityFragment;

public class UtilityField extends Field {

    private Player owner = null;

    public Player getOwner() {
        return owner;
    }

    public UtilityField(String label) {
        super(label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        UtilityFragment rrf = (UtilityFragment) fragmentManager.findFragmentByTag(UtilityFragment.UTILITY_FRAGMENT_TAG);
        rrf.init(gameEngine,this);

        fragmentManager
                .beginTransaction()
                .hide(rollTheDiceFragment)
                .show(rrf)
                .commit();
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

    public void setOwner(Player currentPlayer) {
        this.owner = currentPlayer;
    }

}