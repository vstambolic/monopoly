package com.example.monopoly.game;

import com.example.monopoly.game.custom_views.Monopoly;

public class Player {
    private int id;

    private int currentPosition = 0;
    private String name;
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
}
