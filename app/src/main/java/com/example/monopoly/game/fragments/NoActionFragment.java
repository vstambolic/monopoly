package com.example.monopoly.game.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentNoActionBinding;
import com.example.monopoly.databinding.FragmentRollTheDiceBinding;
import com.example.monopoly.game.roll_the_dice.RollTheDiceService;


public class NoActionFragment extends Fragment {

    public static final String NO_ACTION_FRAGMENT_TAG = "NO_ACTION_FRAGMENT_TAG";
    private FragmentNoActionBinding binding;


    private static final String FIELD_LABEL = "fieldLabel";
    private static final String MESSAGE = "message";

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
        this.binding.fieldLabelTextview.setText(fieldLabel);
    }

    public void setMessage(String message) {
        this.message = message;
        this.binding.messageTextview.setText(message);
    }

    private String fieldLabel;
    private String message;

    public NoActionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fieldLabel Parameter 1.
     * @param message Parameter 2.
     * @return A new instance of fragment NoActionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoActionFragment newInstance(String fieldLabel, String message) {
        NoActionFragment fragment = new NoActionFragment();
        Bundle args = new Bundle();
        args.putString(FIELD_LABEL, fieldLabel);
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fieldLabel = getArguments().getString(FIELD_LABEL);
            message = getArguments().getString(MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentNoActionBinding.inflate(inflater, container, false);

        this.binding.fieldLabelTextview.setText(this.fieldLabel);
        this.binding.messageTextview.setText(this.message);
        return this.binding.getRoot();


    }
}