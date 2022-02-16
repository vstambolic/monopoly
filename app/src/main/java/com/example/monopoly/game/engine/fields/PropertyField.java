package com.example.monopoly.game.engine.fields;


import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.fragments.PropertyFragment;

public class PropertyField extends OwnableField {


    private final int set;
    private int totalSetPieces;
    private final int price;

    private final int rent;
    private final int rentWithColorSet;
    private final int[] rentWithHouse;
    private final int rentWithHotel;

    private final int houseCost;

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

    public PropertyField(int id, String label, int set,int totalSetPieces, int price, int rent, int rentWithColorSet, int[] rentWithHouse, int rentWithHotel, int houseCost, int hotelCost) {
        super(id, label);
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
        action(gameEngine,new PropertyFragment());
    }

    public void setHouseCnt(int i) {
        this.houseCnt = i;
    }

    public void setHotelCnt(int i) {
        this.hotelCnt = i;
    }

    public boolean fullSet() {
        final Player player = this.getOwner();
        int cnt = 0;
        for (PropertyField p : player.getProperties())
            if (p.getSet() == this.set)
                cnt++;
        return cnt == this.getTotalSetPieces();
    }

    @Override
    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--------------------------------------------\n");
        stringBuilder.append("Rent: $").append(this.getRent()).append('\n');
        stringBuilder.append("Rent with color set: $").append(this.getRentWithColorSet()).append('\n');
        stringBuilder.append("Rent with 1 house: $").append(this.getRentWithHouse()[0]).append('\n');
        stringBuilder.append("Rent with 2 houses: $").append(this.getRentWithHouse()[1]).append('\n');
        stringBuilder.append("Rent with 3 houses: $").append(this.getRentWithHouse()[2]).append('\n');
        stringBuilder.append("Rent with 4 houses: $").append(this.getRentWithHouse()[3]).append('\n');
        stringBuilder.append("Rent with hotel: $").append(this.getRentWithHotel()).append('\n');
        stringBuilder.append("--------------------------------------------\n");
        stringBuilder.append("Houses: ").append(this.getHouseCnt()).append('\n');
        stringBuilder.append("Hotels: ").append(this.getHotelCnt()).append('\n');
        if (this.mortgaged)
            stringBuilder.append("\n").append("*mortgaged*");
        return stringBuilder.toString();
    }

    @Override
    public int calculateMortgage() {
        return this.getPrice()/2;
    }

    @Override
    public int calculateLiftMortgage() {
        return 3*this.getPrice()/5;
    }
}

