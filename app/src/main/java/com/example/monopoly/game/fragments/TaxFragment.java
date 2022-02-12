package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.monopoly.databinding.FragmentTaxBinding;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.TaxField;

public class TaxFragment extends ControllerFragment {
    public static final String TAX_FRAGMENT_TAG = "TAX_FRAGMENT_TAG";
    private FragmentTaxBinding binding;

    public TaxFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentTaxBinding.inflate(inflater, container, false);
        this.initView();
        return this.binding.getRoot();
    }

    private void initView() {

        TaxField taxField = (TaxField)this.field;

        this.setFieldLabel(taxField.getLabel());
        this.setMessage(taxField.getMessage());

        this.binding.confirmButton.setOnClickListener(v -> {
            final Player player = this.gameEngine.getCurrentPlayer();
            if (player.getBalance() >= taxField.getTaxValue()) {
                player.decBalance(taxField.getTaxValue());
                gameEngine.setTaxMoney(gameEngine.getTaxMoney() + taxField.getTaxValue());
                gameEngine.getGameFragment().setPlayerBalance(player.getBalance());
                gameEngine.getGameFragment().enableNextTurnButton();
                this.binding.confirmButton.setEnabled(false);
            }
            else {
                if (player.getCapital() >= taxField.getTaxValue()) {
                    Toast.makeText(this.getContext(), "You do not have enough money for this transaction.", Toast.LENGTH_SHORT).show();
                }
                else {
                    player.setIsBankrupt(true);
                    Toast.makeText(this.getContext(), "You have gone bankrupt.\nYou will be eliminated after this turn.", Toast.LENGTH_SHORT).show();
                    gameEngine.getGameFragment().enableNextTurnButton();
                    this.binding.confirmButton.setEnabled(false);
                }
            }
        });


    }



    private void setFieldLabel(String fieldLabel) {
        this.binding.fieldLabelTextview.setText(fieldLabel);
    }
    private void setMessage(String message) {
        this.binding.messageTextview.setText(message);
    }

}