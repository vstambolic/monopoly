package com.example.monopoly.game.engine;

import android.os.Handler;
import android.os.Looper;

import com.example.monopoly.game.GameFragment;
import com.example.monopoly.game.custom_views.Monopoly;
import com.example.monopoly.game.engine.fields.Field;
import com.example.monopoly.game.engine.fields.PropertyField;
import com.example.monopoly.game.engine.fields.RailroadField;
import com.example.monopoly.game.engine.fields.UtilityField;
import com.example.monopoly.game.fragments.RollTheDiceFragment;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameEngine {

    private Monopoly monopolyBoard;
    private GameFragment gameFragment;

    private GameState gameState;

    public GameState getGameState() {
        return this.gameState;
    }

    public static class GameState implements Serializable {

        public GameState(List<Player> players) {
            this.players = players;
        }

        public boolean interruptedFlag=false;
        public int taxMoney = 0;
        public int currentPlayerIndex = 0;
        public List<Player> players;
    }


    public GameEngine(GameFragment gameFragment, Monopoly monopoly, GameState gameState) {
        this.gameState = gameState;

        this.setGameFragment(gameFragment);
        this.setMonopolyBoard(monopoly);
    }

    public void setMonopolyBoard(Monopoly monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
        this.init();
    }

    private void init() {
        this.monopolyBoard.init(this.gameState.players);
        if (this.gameFragment.getChildFragmentManager().getFragments().size() != 0 &&
                        this.gameFragment.getChildFragmentManager().findFragmentByTag(RollTheDiceFragment.ROLL_THE_DICE_TAG) == null || gameState.interruptedFlag) {
            Field.fields[this.gameState.players.get(this.gameState.currentPlayerIndex).getCurrentPosition()].action(this);
            gameState.interruptedFlag = false;
        }

    }

    public Player getCurrentPlayer() {
        return this.gameState.players.get(this.gameState.currentPlayerIndex);
    }


    private Future<?> playerMovingFuture;
    public void stopPlayerMoving() {
        if (playerMovingFuture != null) {
           playerMovingFuture.cancel(true);
           this.gameState.interruptedFlag = true;
        }
    }


    public void moveCurrentPlayer(int diceVal) {
        Handler handler = new Handler(Looper.myLooper());
        final Player currPlayer = this.getCurrentPlayer();

        if (currPlayer.getCurrentPosition() + diceVal >= Field.TOTAL_FIELD_CNT) {
            currPlayer.incBalance(200);
            this.gameFragment.setPlayerBalance(currPlayer.getBalance());
        }

        playerMovingFuture = Executors.newSingleThreadExecutor().submit(() -> {
            for (int i = 0; i < diceVal; i++) {
                this.monopolyBoard.removePlayer(currPlayer);
                currPlayer.incCurrentPosition();
                this.monopolyBoard.addPlayer(currPlayer);
//                SystemClock.sleep(333);
                try {
                    Thread.sleep(333);
                } catch (InterruptedException e) {
                    currPlayer.setCurrentPosition(currPlayer.getCurrentPosition() + diceVal - i-1);
                    return;
                }
            }

            if (!playerMovingFuture.isCancelled()) {
                handler.post(() -> Field.fields[this.gameState.players.get(this.gameState.currentPlayerIndex).getCurrentPosition()].action(this));
            }


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
        return this.gameState.taxMoney;
    }

    public void setTaxMoney(int i) {
        this.gameState.taxMoney = i;
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
        propertyField.setHouseCnt(propertyField.getHouseCnt() + 1);
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
        int rent = RailroadField.calculateRent(railroadField.getOwner().getRailroads().size() - 1);
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
        boolean eliminated = this.getCurrentPlayer().getIsBankrupt();
        eliminated = true;
        if (eliminated)
            this.eliminateCurrentPlayer();

        if (!eliminated && this.getCurrentPlayer().getJailCnt() == 0 && this.getDiceVal() == 12)
            return;

        this.gameState.currentPlayerIndex = (this.gameState.currentPlayerIndex + (eliminated ? 0 : 1)) % this.gameState.players.size();

        while (this.getCurrentPlayer().getJailCnt() != 0) {
            this.getCurrentPlayer().setJailCnt(this.getCurrentPlayer().getJailCnt() - 1);
            this.gameState.currentPlayerIndex = (this.gameState.currentPlayerIndex + 1) % this.gameState.players.size();
        }
    }

    private void eliminateCurrentPlayer() {
        this.getCurrentPlayer().eliminate();
        this.monopolyBoard.removePlayerAndProperties(this.getCurrentPlayer());
        this.gameState.players.remove(this.gameState.currentPlayerIndex);
    }

    public boolean isGameOver() {
        return this.gameState.players.size() == 1;
    }

    public void setGameFragment(GameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }
}
