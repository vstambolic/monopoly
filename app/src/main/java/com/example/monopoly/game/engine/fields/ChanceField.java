package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.ChanceFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;
import com.example.monopoly.game.fragments.UtilityFragment;

public class ChanceField extends Field {

    public ChanceField(String label) {
        super(label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        ChanceFragment rrf = (ChanceFragment) fragmentManager.findFragmentByTag(ChanceFragment.CHANCE_FRAGMENT_TAG);
        rrf.init(gameEngine,this);

        fragmentManager
                .beginTransaction()
                .hide(rollTheDiceFragment)
                .show(rrf)
                .commit();
    }
}