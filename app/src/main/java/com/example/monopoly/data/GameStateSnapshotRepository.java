package com.example.monopoly.data;

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
}
