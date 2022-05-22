package com.example.monopoly.game;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.example.monopoly.game.engine.GameEngine;

public class GameViewModel extends ViewModel {

    public static final String GAME_STATE = "GAME_STATE";
    public static final String GAME_ID = "GAME_ID";
    public static final String CURRENT_GAME_STATE_INDEX = "CURRENT_GAME_STATE_INDEX";


    private GameEngine.GameState gameState;
    private long gameId;
    private long currGameStateIndex;

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return this.gameId;
    }
    public long getCurrGameStateIndex() {
        return currGameStateIndex;
    }

    public void setCurrGameStateIndex(long currGameStateIndex) {
        this.currGameStateIndex = currGameStateIndex;
    }
    public GameEngine.GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameEngine.GameState gameState) {
        this.gameState = gameState;
    }


    public void initByInstanceStateBundle(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(GAME_STATE)) {
                this.gameState = (GameEngine.GameState) bundle.getSerializable(GAME_STATE);
            }
            if (bundle.containsKey(GAME_ID)) {
                this.gameId = bundle.getLong(GAME_ID);
            }
            if (bundle.containsKey(CURRENT_GAME_STATE_INDEX)) {
                this.currGameStateIndex = bundle.getLong(CURRENT_GAME_STATE_INDEX);
            }
        }
    }



}
