package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentRailroadBinding;
import com.example.monopoly.game.Constants;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.RailroadField;


public class RailroadFragment extends Fragment {

    public static final String RAILROAD_FRAGMENT_TAG = "RAILROAD_FRAGMENT_TAG";
    private FragmentRailroadBinding binding;
    private GameEngine gameEngine;
    private RailroadField railroadField;

    public RailroadFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentRailroadBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    public void init(GameEngine gameEngine, RailroadField railroadField) {
        this.gameEngine = gameEngine;
        this.railroadField =  railroadField;

        this.binding.fieldLabelTextview.setText(railroadField.getLabel());

        if (!this.railroadField.hasOwner()) {
            Button button = new Button(this.getContext());
            button.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
            button.setText("BUY FOR $200");
            button.setOnClickListener(v -> {
                if (this.gameEngine.getCurrentPlayer().getBalance() >= 200) {
                    button.setEnabled(false);
                    this.gameEngine.markAsBought(railroadField);
                }
                else
                    Toast.makeText(this.getContext(),"You don't have enough money for this transaction.",Toast.LENGTH_SHORT).show();
            });
            this.binding.buttonWrapperLinearLayout.addView(button);
        }
        else {
            if (this.railroadField.getOwner().equals(this.gameEngine.getCurrentPlayer())) {
                TextView textView = new TextView(getContext());
                textView.setText("You already own this field.");
                this.binding.buttonWrapperLinearLayout.addView(textView);

            }
            else {
                Button payRentButton = new Button(this.getContext());
                payRentButton.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
                final Player player = this.gameEngine.getCurrentPlayer();
                int rent = RailroadField.calculateRent(player.getRailroads().size());

                payRentButton.setText("PAY RENT ($" + rent+")");
                payRentButton.setOnClickListener(v -> {

                    if (player.getBalance() >= rent) {
                        gameEngine.payRent(railroadField);
                        payRentButton.setEnabled(false);
                    }
                    else {
                        if (player.getCapital() >= rent) {
                            Toast.makeText(this.getContext(), "You do not have enough money for rent.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            player.setIsBankrupt(true);
                            Toast.makeText(this.getContext(), "You have gone bankrupt.\nYou will be eliminated after this turn.", Toast.LENGTH_SHORT).show();
                            gameEngine.getGameFragment().enableNextTurnButton();
                            payRentButton.setEnabled(false);
                        }
                    }
                });
                this.binding.buttonWrapperLinearLayout.addView(payRentButton);
            }
        }

    }
}