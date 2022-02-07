package com.example.monopoly.game;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.example.monopoly.game.custom_views.Monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class GameEngine {
    private static final int TOTAL_FIELD_CNT = 40;

    private final Monopoly monopolyBoard;
    private int currentPlayer = 0;
    private List<Player> players;

    public GameEngine(Monopoly monopoly, List<Player> players) {
        this.players = players;
        this.monopolyBoard = monopoly;
        this.init();
    }


    private void init() {
        for (int i = 0; i < this.players.size(); i++)
            this.monopolyBoard.addPlayer(this.players.get(i));
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayer);
    }

    public void moveCurrentPlayer(int diceVal) {
        Handler handler = new Handler(Looper.myLooper());
        final Player currPlayer = this.getCurrentPlayer();
        Executors.newSingleThreadExecutor().submit(()->{
//            handler.post(()->{
                for (int i = 0; i < diceVal; i++) {
                    this.monopolyBoard.removePlayer(currPlayer);
                    currPlayer.incCurrentPosition();
                    this.monopolyBoard.addPlayer(currPlayer);
                    try {
                        Thread.sleep(333);
                    }
                    catch (InterruptedException e) {
                        return;
                    }
                }
            });
//        });


    }
}
