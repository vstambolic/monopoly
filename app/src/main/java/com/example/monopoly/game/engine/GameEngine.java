package com.example.monopoly.game.engine;

import android.os.Handler;
import android.os.Looper;

import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.custom_views.Monopoly;
import com.example.monopoly.game.engine.fields.Field;
import com.example.monopoly.game.engine.fields.PropertyField;
import com.example.monopoly.game.engine.fields.RailroadField;
import com.example.monopoly.game.engine.fields.UtilityField;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;

public class GameEngine implements Serializable {

    private int taxMoney = 0;
    private final Monopoly monopolyBoard;
    private int currentPlayerIndex = 0;
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
        return this.players.get(this.currentPlayerIndex);
    }

    public void moveCurrentPlayer(int diceVal) {
        Handler handler = new Handler(Looper.myLooper());
        final Player currPlayer = this.getCurrentPlayer();

        if (currPlayer.getCurrentPosition() + diceVal > Field.TOTAL_FIELD_CNT) {
            currPlayer.incBalance(200);
            this.gameFragment.setPlayerBalance(currPlayer.getBalance());
        }

        Executors.newSingleThreadExecutor().submit(()->{
                for (int i = 0; i < diceVal; i++) {
                    this.monopolyBoard.removePlayer(currPlayer);
                    currPlayer.incCurrentPosition();
                    this.monopolyBoard.addPlayer(currPlayer);
                    try {
                        Thread.sleep(333);
                    }
                    catch (InterruptedException e) {
                        currPlayer.setCurrentPosition(currPlayer.getCurrentPosition() + diceVal - i); // todo idk if this works
                        return;
                    }
                }
           handler.post(()->{
               Field.fields[players.get(currentPlayerIndex).getCurrentPosition()].action(this);
           });
        });


    }

    public GameFragment getGameFragment() {
        return this.gameFragment;
    }

    public void moveToField(int fieldNumber) {
        final Player currPlayer = this.getCurrentPlayer();
        if (fieldNumber < currPlayer.getCurrentPosition()) {
            currPlayer.incBalance(200);
            this.gameFragment.setPlayerBalance(currPlayer.getBalance());
        }
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

    public void markAsBought(PropertyField propertyField) {
        this.getCurrentPlayer().addProperty(propertyField);
        this.getCurrentPlayer().incBalance(-propertyField.getPrice());
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        propertyField.setOwner(this.getCurrentPlayer());
        this.monopolyBoard.markAsBought(this.getCurrentPlayer());
    }

    public void markAsBought(RailroadField railroadField) {
        this.getCurrentPlayer().addRailroad(railroadField);
        this.getCurrentPlayer().decBalance(200);
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        railroadField.setOwner(this.getCurrentPlayer());
        this.monopolyBoard.markAsBought(this.getCurrentPlayer());
    }

    public void markAsBought(UtilityField utilityField) {
        this.getCurrentPlayer().addUtility(utilityField);
        this.getCurrentPlayer().decBalance(150);
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        utilityField.setOwner(this.getCurrentPlayer());
        this.monopolyBoard.markAsBought(this.getCurrentPlayer());
    }

    public void houseBought(PropertyField propertyField) {
        propertyField.setHouseCnt(propertyField.getHouseCnt()+1);
        this.getCurrentPlayer().incBalance(-propertyField.getHouseCost());
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        this.monopolyBoard.houseBought(this.getCurrentPlayer());
    }

    public void hotelBought(PropertyField propertyField) {
        propertyField.setHotelCnt(propertyField.getHotelCnt() + 1);
        propertyField.setHouseCnt(0);

        this.getCurrentPlayer().incBalance(-propertyField.getHotelCost());
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());

        this.monopolyBoard.hotelBought(this.getCurrentPlayer());

    }

    public void payRent(PropertyField propertyField) {
        int rent = propertyField.calculateRent();
        this.getCurrentPlayer().decBalance(rent);
        propertyField.getOwner().incBalance(rent);
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        this.gameFragment.enableNextTurnButton();
    }

    public void payRent(RailroadField railroadField) {
        int rent = RailroadField.calculateRent(railroadField.getOwner().getRailroads().size()-1);
        this.getCurrentPlayer().decBalance(rent);
        railroadField.getOwner().incBalance(rent);
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        this.gameFragment.enableNextTurnButton();
    }

    public void payRent(UtilityField utilityField) {
        int rent = (utilityField.getOwner().getUtilities().size() == 2 ? 10 : 4) * getDiceVal();
        this.getCurrentPlayer().decBalance(rent);
        utilityField.getOwner().incBalance(rent);
        this.gameFragment.setPlayerBalance(this.getCurrentPlayer().getBalance());
        this.gameFragment.enableNextTurnButton();
    }

    public int getDiceVal() {
        return this.gameFragment.getDiceVal();
    }

    public void nextTurn() {
        // TODO if 12

        boolean eliminated = this.getCurrentPlayer().getIsBankrupt();
        if (eliminated)
            this.eliminateCurrentPlayer();
        this.currentPlayerIndex = (this.currentPlayerIndex + (eliminated?0:1)) % this.players.size();

        while (this.getCurrentPlayer().getJailCnt() != 0) {
            this.getCurrentPlayer().setJailCnt(this.getCurrentPlayer().getJailCnt() - 1);
            this.currentPlayerIndex = (currentPlayerIndex + 1) % this.players.size();
        }
    }

    private void eliminateCurrentPlayer() {
        this.players.remove(this.currentPlayerIndex);
    }

    public boolean isGameOver() {
        return this.players.size() == 1;
    }
}
