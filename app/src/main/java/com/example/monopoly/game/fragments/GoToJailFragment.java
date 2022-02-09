package com.example.monopoly.game.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentGoToJailBinding;
import com.example.monopoly.databinding.FragmentNoActionBinding;
import com.example.monopoly.databinding.FragmentRollTheDiceBinding;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.roll_the_dice.RollTheDiceService;


public class GoToJailFragment extends ControllerFragment {

    public static final String GO_TO_JAIL_FRAGMENT_TAG = "GO_TO_JAIL_FRAGMENT_TAG";
    private FragmentGoToJailBinding binding;

    public GoToJailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentGoToJailBinding.inflate(inflater, container, false);
        this.initView();
        return this.binding.getRoot();
    }

    private void initView() {
        this.binding.confirmButton.setOnClickListener(v -> {
            gameEngine.getGameFragment().enableNextTurnButton();
            gameEngine.getCurrentPlayer().setJailCnt(3);
            gameEngine.moveToField(10);
        });
    }
}