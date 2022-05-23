package com.example.monopoly.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.monopoly.game.engine.GameEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameStateSnapshotRepository {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final GameStateSnapshotDao dao;
    public GameStateSnapshotRepository(GameStateSnapshotDao dao) {
        this.dao = dao;
    }

    public void insert(GameStateSnapshot gameStateSnapshot) {
        executorService.submit(() -> dao.insert(gameStateSnapshot));
    }


    private MutableLiveData< GameEngine.GameState> gameState = new MutableLiveData<>(null);
    public LiveData<GameEngine.GameState> getGameState() {
        return this.gameState;
    }

    public void getNextGameState(long gameId, long index) {
        executorService.submit(() -> {
            GameEngine.GameState gs = this.dao.getGameState(gameId, index);
            this.gameState.postValue(gs);
        });
    }
}
