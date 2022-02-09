package com.example.monopoly.game.fragments;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.databinding.FragmentNoActionBinding;
import com.example.monopoly.game.engine.fields.NoActionField;

public class NoActionFragment extends ControllerFragment {

    public static final String NO_ACTION_FRAGMENT_TAG = "NO_ACTION_FRAGMENT_TAG";
    private FragmentNoActionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.binding = FragmentNoActionBinding.inflate(inflater, container, false);

        this.binding.fieldLabelTextview.setText(field.getLabel());
        this.binding.messageTextview.setText(((NoActionField)field).getMessage());

        return this.binding.getRoot();
    }

}