package com.example.monopoly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.databinding.FragmentGameOverBinding;
import com.example.monopoly.databinding.FragmentNewGameBinding;
import com.example.monopoly.game.GameFragmentArgs;
import com.example.monopoly.game.engine.Player;


public class GameOverFragment extends Fragment {


    private FragmentGameOverBinding binding;
    private Player player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.player = GameOverFragmentArgs.fromBundle(requireArguments()).getPlayer();

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGameOverBinding.inflate(inflater, container, false);
        binding.layoutGameOver.setOnClickListener(view1 -> {
            NavHostFragment
                    .findNavController(GameOverFragment.this)
                    .navigate(GameOverFragmentDirections.actionGameOverFragmentToHistoryFragment());
        });
        binding.textviewPlayerWinner.setText(this.player.getName() + " won!");
        binding.textviewBalance.setText("Balance: $" + this.player.getBalance());
        binding.textviewTotalProperties.setText("Properties: " + this.player.getOwnables().size());
        binding.textviewTotalCapital.setText("Total capital: $" + this.player.getCapital());


        return binding.getRoot();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}