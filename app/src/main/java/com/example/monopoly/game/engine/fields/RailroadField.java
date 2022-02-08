package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.PropertyFragment;
import com.example.monopoly.game.fragments.RailroadFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;

public class RailroadField extends Field {

    private static final int RENT = 25;
    public static int calculateRent(int cnt) {
        return RENT * (1<<cnt);
    }

    private Player owner = null;

    public Player getOwner() {
        return owner;
    }

    public RailroadField(String label) {
        super(label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        RailroadFragment rrf = (RailroadFragment) fragmentManager.findFragmentByTag(RailroadFragment.RAILROAD_FRAGMENT_TAG);
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
