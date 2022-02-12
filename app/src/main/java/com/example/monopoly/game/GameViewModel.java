package com.example.monopoly.game;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.example.monopoly.game.engine.GameEngine;

public class GameViewModel extends ViewModel {

    public static final String GAME_STATE = "GAME_STATE";

    public GameEngine.GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameEngine.GameState gameState) {
        this.gameState = gameState;
    }

    private GameEngine.GameState gameState;


    public void initByInstanceStateBundle(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(GAME_STATE)) {
                this.gameState = gameState;
            }
        }
    }



}
