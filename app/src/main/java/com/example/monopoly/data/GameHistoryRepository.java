package com.example.monopoly.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GameHistoryRepository {

    private final GameHistoryDao gameHistoryDao;

    public GameHistoryRepository(GameHistoryDao gameHistoryDao) {
        this.gameHistoryDao = gameHistoryDao;
    }

    public long insert(GameHistory gameHistory) {
        return gameHistoryDao.insert(gameHistory);
    }

    public List<GameHistory> getAll() {
        return gameHistoryDao.getAll();
    }

    public LiveData<List<GameHistory>> getAllLiveData() {
        return gameHistoryDao.getAllLiveData();
    }

}
