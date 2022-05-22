package com.example.monopoly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.data.Game;
import com.example.monopoly.data.GameRepository;
import com.example.monopoly.data.MonopolyDatabase;
import com.example.monopoly.databinding.FragmentNewGameBinding;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class NewGameFragment extends Fragment {

    private FragmentNewGameBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentNewGameBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rangeSlider.addOnChangeListener((slider, value, fromUser) -> {
            switch ((int)value){
                case 2:
                    binding.editTextPlayer3.setVisibility(View.INVISIBLE);
                    binding.editTextPlayer4.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    binding.editTextPlayer3.setVisibility(View.VISIBLE);
                    binding.editTextPlayer4.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    binding.editTextPlayer3.setVisibility(View.VISIBLE);
                    binding.editTextPlayer4.setVisibility(View.VISIBLE);
                    break;
            }
        });
        binding.buttonStart.setOnClickListener(view1 -> {
            NavHostFragment
                    .findNavController(NewGameFragment.this)
                    .navigate(NewGameFragmentDirections
                            .actionNewGameFragmentToGameFragment(this.getPlayers()));
        });
    }

    private String[] getPlayers() {
        int numberOfPlayers = this.binding.rangeSlider.getValues().get(0).intValue();
        String[] players = new String[numberOfPlayers];

        String player1 =  this.binding.editTextPlayer1.getText().toString();
        players[0] = player1.length() > 0? player1:"Player #1";

        String player2 =  this.binding.editTextPlayer2.getText().toString();
        players[1] = player2.length() > 0? player2:"Player #2";

        if (numberOfPlayers>2) {
            String player3 = this.binding.editTextPlayer3.getText().toString();
            players[2] = player3.length() > 0? player3:"Player #3";
        }
        if (numberOfPlayers>3) {
            String player4 = this.binding.editTextPlayer4.getText().toString();
            players[3] = player4.length() > 0? player4:"Player #4";
        }

        return players;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}