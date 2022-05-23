package com.example.monopoly.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    long insert(Game history);

    @Query("SELECT * FROM Game")
    List<Game> getAll();

    @Query("SELECT * FROM Game")
    LiveData<List<Game>> getAllLiveData();

    @Query("DELETE FROM Game WHERE id = :id")
    void delete(long id);

    @Query("UPDATE Game SET winner = :winnerId, duration = :l WHERE id = :gameId")
    void updateGame(long gameId, int winnerId, long l);

    @Query("SELECT * FROM Game WHERE id = :gameId")
    Game getGame(long gameId);
}
