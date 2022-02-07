package com.example.monopoly.game.engine;

import com.example.monopoly.game.custom_views.Monopoly;

public class Player {
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
}
