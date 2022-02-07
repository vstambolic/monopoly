package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.NoActionFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;

public class NoActionField extends Field{

    private String message;
    public NoActionField(String label, String message) {
        super(label);
        this.message = message;
    }

    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        NoActionFragment noActionFragment = (NoActionFragment)fragmentManager.findFragmentByTag(NoActionFragment.NO_ACTION_FRAGMENT_TAG);
        noActionFragment.setFieldLabel(this.label);
        noActionFragment.setMessage(this.message);
        fragmentManager
                .beginTransaction()
                .hide(rollTheDiceFragment)
                .show(noActionFragment)
                .commit();
    }
}
