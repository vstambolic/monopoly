package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.PropertyFragment;
import com.example.monopoly.game.fragments.RollTheDiceFragment;

public class PropertyField extends Field {

    private Player owner = null;

    private final int set;
    private int totalSetPieces;
    private final int price;

    private final int rent;
    private final int rentWithColorSet;
    private final int[] rentWithHouse;
    private final int rentWithHotel;

    private final int houseCost;

    public Player getOwner() {
        return owner;
    }

    public int getSet() {
        return set;
    }

    public int getTotalSetPieces() {
        return totalSetPieces;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    public int getRentWithColorSet() {
        return rentWithColorSet;
    }

    public int[] getRentWithHouse() {
        return rentWithHouse;
    }

    public int getRentWithHotel() {
        return rentWithHotel;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    public int getHouseCnt() {
        return houseCnt;
    }

    public int getHotelCnt() {
        return hotelCnt;
    }

    private final int hotelCost;

    private int houseCnt = 0;
    private int hotelCnt = 0;

    public PropertyField(String label, int set,int totalSetPieces, int price, int rent, int rentWithColorSet, int[] rentWithHouse, int rentWithHotel, int houseCost, int hotelCost) {
        super(label);
        this.set = set;
        this.totalSetPieces = totalSetPieces;
        this.price = price;
        this.rent = rent;
        this.rentWithColorSet = rentWithColorSet;
        this.rentWithHouse = rentWithHouse;
        this.rentWithHotel = rentWithHotel;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
    }

    public int calculateRent() {
        if (this.hotelCnt > 0 || this.houseCnt > 0) {
            int r = 0;

            if (this.hotelCnt > 0) {
                r+=this.rentWithHotel*this.hotelCnt;
            }
            if (this.houseCnt > 0) {
                r+=this.rentWithHouse[this.houseCnt-1];
            }
            return r;
        }
        else {
            if (this.owner != null) {
                int cnt = 0;
                for (PropertyField p : this.owner.getProperties()) {
                        if (p.set == this.set)
                            cnt++;
                }
                if (cnt == this.totalSetPieces)
                    return this.rentWithColorSet;
            }

            return this.rent;
        }
    }


    @Override
    public void action(GameEngine gameEngine) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        Fragment rollTheDiceFragment = fragmentManager.findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG);
        PropertyFragment propertyFragment = (PropertyFragment) fragmentManager.findFragmentByTag(PropertyFragment.PROPERTY_FRAGMENT_TAG);
        propertyFragment.init(gameEngine,this);

        fragmentManager
                .beginTransaction()
                .hide(rollTheDiceFragment)
                .show(propertyFragment)
                .commit();
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

    public void setOwner(Player currentPlayer) {
        this.owner = currentPlayer;
    }

    public void setHouseCnt(int i) {
        this.houseCnt = i;
    }

    public void setHotelCnt(int i) {
        this.hotelCnt = i;
    }
}
