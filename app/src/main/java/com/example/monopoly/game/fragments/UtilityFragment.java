package com.example.monopoly.game.fragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentUtilityBinding;
import com.example.monopoly.game.Constants;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.UtilityField;


public class UtilityFragment extends ControllerFragment {

    public static final String UTILITY_FRAGMENT_TAG = "UTILITY_FRAGMENT_TAG";
    private FragmentUtilityBinding binding;

    public UtilityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentUtilityBinding.inflate(inflater, container, false);
        this.initView();
        return this.binding.getRoot();
    }

    public void initView() {
        super.initView();
        UtilityField utilityField = (UtilityField)this.field;

        this.binding.fieldLabelTextview.setText(utilityField.getLabel());

        if (!utilityField.hasOwner()) {
            gameEngine.getGameFragment().enableNextTurnButton();


            Button button = new Button(this.getContext());
            button.setBackgroundResource(R.drawable.tags_rounded_corners);
            ((GradientDrawable) button.getBackground()).setColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
            button.setText("BUY FOR $150");
            button.setOnClickListener(v -> {
                if (this.gameEngine.getCurrentPlayer().getBalance() >= 150) {
                    button.setEnabled(false);
                    this.gameEngine.markAsBought(utilityField);
                }
                else
                    Toast.makeText(this.getContext(),"You don't have enough money for this transaction.",Toast.LENGTH_SHORT).show();
            });
            this.binding.buttonWrapperLinearLayout.addView(button);
        }
        else {
            if (utilityField.getOwner().equals(this.gameEngine.getCurrentPlayer())) {
                TextView textView = new TextView(getContext());
                textView.setText("You already own this field.");
                this.binding.buttonWrapperLinearLayout.addView(textView);

            }
            else {
                Button payRentButton = new Button(this.getContext());
                payRentButton.setBackgroundResource(R.drawable.tags_rounded_corners);
                ((GradientDrawable) payRentButton.getBackground()).setColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
                final Player player = this.gameEngine.getCurrentPlayer();
                int rent = (utilityField.getOwner().getUtilities().size() == 2 ? 10 : 4) * gameEngine.getDiceVal();
                payRentButton.setText("PAY RENT ($" + rent +")");
                payRentButton.setOnClickListener(v -> {

                    if (player.getBalance() >= rent) {
                        gameEngine.payRent(utilityField);
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