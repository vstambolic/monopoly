package com.example.monopoly.game.simulation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.databinding.ViewSimulationPlayerContainerBinding;
import com.example.monopoly.game.Constants;
import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.GameFragmentDirections;
import com.example.monopoly.game.GameSimulationFragment;
import com.example.monopoly.game.GameSimulationFragmentDirections;
import com.example.monopoly.game.engine.Player;

public class GameSimulationPlayerView extends ConstraintLayout {

    private ViewSimulationPlayerContainerBinding binding;

    private Player player;
    private GameSimulationFragment gameSimulationFragment;

    public GameSimulationPlayerView(@NonNull Context context, GameSimulationFragment gameSimulationFragment, Player player) {
        super(context);
        this.player = player;
        this.gameSimulationFragment = gameSimulationFragment;
        this.init(context);
    }

    private void init(Context context) {
        this.binding = ViewSimulationPlayerContainerBinding.inflate(LayoutInflater.from(context), this, true);
        this.binding.playerNameTextview.setText(player.getName());
        this.binding.playerNameTextview.setTextColor(Constants.PLAYER_COLORS[player.getId()]);
        this.binding.balanceTextview.setText("Balance: $" + player.getBalance());
        this.binding.propertyCntTextview.setText("Properties: " + player.getOwnables().size());
        this.setOnClickListener(view -> NavHostFragment
                .findNavController(gameSimulationFragment)
                .navigate(GameSimulationFragmentDirections.actionGameSimulationFragmentToPlayerInfoFragment(player)));

    }


}
