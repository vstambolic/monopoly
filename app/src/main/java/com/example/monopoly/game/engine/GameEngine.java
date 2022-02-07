package com.example.monopoly.game.engine;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.custom_views.Monopoly;
import com.example.monopoly.game.engine.fields.Field;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameEngine {

    private int taxMoney = 0;
    private final Monopoly monopolyBoard;
    private int currentPlayer = 0;
    private GameFragment gameFragment;
    private List<Player> players;

    public GameEngine(GameFragment gameFragment, Monopoly monopoly, List<Player> players) {
        this.gameFragment = gameFragment;
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

        if (currPlayer.getCurrentPosition() + diceVal > Field.TOTAL_FIELD_CNT)
            currPlayer.incBalance(200);

        Executors.newSingleThreadExecutor().submit(()->{
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
           handler.post(()->{
               Field.fields[players.get(currentPlayer).getCurrentPosition()].action(this);
           });
        });


    }

    public GameFragment getGameFragment() {
        return this.gameFragment;
    }

    public void moveToField(int fieldNumber) {
        final Player currPlayer = this.getCurrentPlayer();
        this.monopolyBoard.removePlayer(currPlayer);
        currPlayer.setCurrentPosition(fieldNumber);
        this.monopolyBoard.addPlayer(currPlayer);



    }

    public int getTaxMoney() {
            return this.taxMoney;
    }

    public void setTaxMoney(int i) {
        this.taxMoney= i;
    }
}
