package com.example.monopoly.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRepository {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final GameDao gameDao;

    public GameRepository(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    private MutableLiveData<Long> insertedId = new MutableLiveData<>(-1l);

    public LiveData<Long> getInsertedId() {
        return insertedId;
    }

    public void insert(Game game) {
        executorService.submit(() -> {
            long id = gameDao.insert(game);
            insertedId.postValue(id);
        });
    }

    public List<Game> getAll() {
        return gameDao.getAll();
    }

    public LiveData<List<Game>> getAllLiveData() {
        return gameDao.getAllLiveData();
    }

    public void delete(long id) {
        executorService.submit(()-> this.gameDao.delete(id));
    }

    public void updateGame(long gameId, int winnerId, Date endDate) {
        executorService.submit(() -> {
            Game game = GameRepository.this.getGame(gameId);
            gameDao.updateGame(gameId, winnerId, endDate.getTime() - game.getDate().getTime());
        });
    }

    private Game getGame(long gameId) {
        return this.gameDao.getGame(gameId);
    }
}
