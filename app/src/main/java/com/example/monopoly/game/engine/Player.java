package com.example.monopoly.game.engine;

import android.view.Menu;

import androidx.collection.ArraySet;

import com.example.monopoly.game.custom_views.Monopoly;
import com.example.monopoly.game.engine.fields.PropertyField;
import com.example.monopoly.game.engine.fields.RailroadField;
import com.example.monopoly.game.engine.fields.UtilityField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements Serializable {
    private int id;

    private int balance = 1500;
    private int currentPosition = 0;
    private String name;
    private int jailCnt = 0;
    private boolean isBankrupt = false;

    public Player(String name, int id) {
        this.name = name;
        this.id =id;
    }

    private List<PropertyField> properties = new ArrayList<>();
    private List<RailroadField> railroads = new ArrayList<>();

    public List<UtilityField> getUtilities() {
        return utilities;
    }

    private List<UtilityField> utilities = new ArrayList<>();


    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void incCurrentPosition() {
        this.currentPosition = (this.currentPosition + 1) % Monopoly.TOTAL_FIELD_CNT;
    }

    public void incBalance(int increment) {
        this.balance+=increment;
    }

    public void setCurrentPosition(int fieldNumber) {
        this.currentPosition = fieldNumber;
    }

    public void setJailCnt(int i) {
        this.jailCnt = i;
    }

    public int getCapital() {
        int sum = this.balance;
        for (int i = 0; i < 55; i++) {
            sum += 1000; // todo calculate capital/net worth
        }
        return sum;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setIsBankrupt(boolean b) {
        this.isBankrupt = true;
    }

    public List<PropertyField> getProperties() {
        return this.properties;
    }

    public void addProperty(PropertyField propertyField) {
        this.properties.add(propertyField);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void decBalance(int rent) {
        this.incBalance(-rent);
    }

    public List<RailroadField> getRailroads() {
        return this.railroads;
    }

    public void addRailroad(RailroadField railroadField) {
        this.railroads.add(railroadField);
    }

    public void addUtility(UtilityField utilityField) {
        this.utilities.add(utilityField);
    }

    public boolean getIsBankrupt() {
        return this.isBankrupt;
    }

    public int getJailCnt() {
        return this.jailCnt;
    }
}
