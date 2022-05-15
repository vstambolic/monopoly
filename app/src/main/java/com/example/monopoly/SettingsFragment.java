package com.example.monopoly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.monopoly.databinding.FragmentSettingsBinding;
import com.example.monopoly.game.Constants;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.util.Objects;
import java.util.logging.Logger;


public class SettingsFragment extends Fragment {


    public static final String SHAKE_VIBRATION = "SHAKE_VIBRATION";
    private FragmentSettingsBinding binding;
    private SharedPreferences sharedPreferences;
    public static final String SHAKE_SENSITIVITY = "SHAKE_SENSITIVITY";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.requireContext().getSharedPreferences(Constants.DICE_ROLL_SENSITIVITY_SP_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        if (sharedPreferences.contains(SHAKE_SENSITIVITY))
            this.binding.rangeSlider.setValues((float) sharedPreferences.getInt(SHAKE_SENSITIVITY, 1));
        else {
            this.sharedPreferences.edit().putInt(SHAKE_SENSITIVITY, 1).commit();
            this.binding.rangeSlider.setValues(1f);
        }


        if (sharedPreferences.contains(SHAKE_VIBRATION))
            this.binding.checkbox.setChecked(sharedPreferences.getBoolean(SHAKE_VIBRATION, false));
        else {
            this.sharedPreferences.edit().putBoolean(SHAKE_VIBRATION, false).commit();
            this.binding.checkbox.setChecked(false);
        }


        this.binding.rangeSlider.addOnChangeListener((slider, value, fromUser) -> this.sharedPreferences.edit().putInt(SHAKE_SENSITIVITY, (int) value).commit());
        this.binding.checkbox.setOnCheckedChangeListener((compoundButton, b) -> this.sharedPreferences.edit().putBoolean(SHAKE_VIBRATION, b).commit());


        this.binding.rangeSlider.setLabelFormatter(value -> {
            switch ((int) value) {
                case 0:  return "low";
                case 1:  return "normal";
                case 2: return "high";
                default: return "";
            }
        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}