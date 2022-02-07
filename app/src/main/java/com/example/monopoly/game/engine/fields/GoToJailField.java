package com.example.monopoly.game.engine.fields;


import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.GoToJailFragment;
import com.example.monopoly.game.fragments.NoActionFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;

public class GoToJailField extends Field {

    public GoToJailField(String label) {
        super(label);
    }

    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        GoToJailFragment goToJailFragment = (GoToJailFragment) fragmentManager.findFragmentByTag(GoToJailFragment.GO_TO_JAIL_FRAGMENT_TAG);

        goToJailFragment.setGameEngine(gameEngine);

        fragmentManager
                .beginTransaction()
                .hide(rollTheDiceFragment)
                .show(goToJailFragment)
                .commit();
    }
}
