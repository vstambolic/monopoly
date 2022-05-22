package com.example.monopoly.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private Date date;
    private long duration;
    private ArrayList<String> players;
    private int winner;

    public Game(long id, Date date, long duration, ArrayList<String> players, int winner) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.players = players;
        this.winner = winner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
