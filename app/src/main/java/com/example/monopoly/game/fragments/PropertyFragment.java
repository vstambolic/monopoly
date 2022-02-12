package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.controls.Control;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentPropertyBinding;
import com.example.monopoly.databinding.FragmentPropertyBinding;
import com.example.monopoly.game.Constants;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.PropertyField;


public class PropertyFragment extends ControllerFragment {

    public static final String PROPERTY_FRAGMENT_TAG = "PROPERTY_FRAGMENT_TAG";
    private FragmentPropertyBinding binding;


    public PropertyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentPropertyBinding.inflate(inflater, container, false);
        this.initView();
        return this.binding.getRoot();
    }

    private void initView() {
        PropertyField propertyField = (PropertyField)this.field;
        this.binding.fieldLabelTextview.setText(propertyField.getLabel());
        this.binding.rentTextview.setText("Rent: $" + propertyField.getRent());
        this.binding.rentWithColorSetTextview.setText("Rent with color set: $" + propertyField.getRentWithColorSet());
        this.binding.rentWithHouse1Textview.setText("Rent with 1 house: $" + propertyField.getRentWithHouse()[0]);
        this.binding.rentWithHouse2Textview.setText("Rent with 2 houses: $" + propertyField.getRentWithHouse()[1]);
        this.binding.rentWithHouse3Textview.setText("Rent with 3 houses: $" + propertyField.getRentWithHouse()[2]);
        this.binding.rentWithHouse4Textview.setText("Rent with 4 houses: $" + propertyField.getRentWithHouse()[3]);
        this.binding.rentWithHotelTextview.setText("Rent with hotel: $" + propertyField.getRentWithHotel());


        if (!propertyField.hasOwner()) {
            this.gameEngine.getGameFragment().enableNextTurnButton();
            Button button = new Button(this.getContext());
            button.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
            button.setText("BUY FOR $"+propertyField.getPrice());
            button.setOnClickListener(v -> {
                if (this.gameEngine.getCurrentPlayer().getBalance() >= propertyField.getPrice()) {
                    button.setEnabled(false);
                    this.gameEngine.markAsBought(propertyField);
                }
                else
                    Toast.makeText(this.getContext(),"You don't have enough money for this transaction.",Toast.LENGTH_SHORT).show();
            });
            this.binding.buttonWrapperLinearLayout.addView(button);
        }
        else {
            if (propertyField.getOwner().equals(this.gameEngine.getCurrentPlayer())) {
                if (propertyField.fullSet()) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 0, 0, 10);

                    Button buyHouseButton = new Button(this.getContext());
                    buyHouseButton.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
                    buyHouseButton.setLayoutParams(params);
                    buyHouseButton.setText("BUY HOUSE FOR $" + propertyField.getHouseCost());
                    buyHouseButton.setOnClickListener(v -> {
                        if (propertyField.getHouseCnt() == 4) {
                            Toast.makeText(getContext(),"You already have 4 houses.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        if (propertyField.getHotelCnt() == 4) {
                            Toast.makeText(getContext(),"You already have 4 hotels.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        if (this.gameEngine.getCurrentPlayer().getBalance() < propertyField.getHouseCost()) {
                            Toast.makeText(this.getContext(),"You don't have enough money for this transaction.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            gameEngine.houseBought(propertyField);
                        }
                    });

                    this.binding.buttonWrapperLinearLayout.addView(buyHouseButton);

                    Button buyHotelButton = new Button(this.getContext());
                    buyHotelButton.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
                    buyHotelButton.setText("BUY HOTEL FOR $" + propertyField.getHotelCost());
                    buyHotelButton.setOnClickListener(v -> {
                        if (propertyField.getHouseCnt() < 4) {
                            Toast.makeText(getContext(),"You must have 4 house before building a hotel.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        if (propertyField.getHotelCnt() == 4) {
                            Toast.makeText(getContext(),"You already have 4 hotels.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        if (this.gameEngine.getCurrentPlayer().getBalance() < propertyField.getHotelCost()) {
                            Toast.makeText(this.getContext(),"You don't have enough money for this transaction.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            gameEngine.hotelBought(propertyField);
                        }
                    });

                    this.binding.buttonWrapperLinearLayout.addView(buyHotelButton);
                }
                else {
                    TextView textView = new TextView(this.getContext());
                    textView.setText("You own this property");
                    this.binding.buttonWrapperLinearLayout.addView(textView);
                }

            }
            else {
                Button payRentButton = new Button(this.getContext());
                payRentButton.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);
                payRentButton.setText("PAY RENT ($" + propertyField.calculateRent()+")");
                payRentButton.setOnClickListener(v -> {
                    final Player player = this.gameEngine.getCurrentPlayer();

                    int rent = propertyField.calculateRent();
                    if (player.getBalance() >= rent) {
                        gameEngine.payRent(propertyField);
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