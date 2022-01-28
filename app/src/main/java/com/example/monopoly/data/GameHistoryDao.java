package com.example.monopoly.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameHistoryDao {

    @Insert
    long insert(GameHistory history);

    @Query("SELECT * FROM GameHistory")
    List<GameHistory> getAll();

    @Query("SELECT * FROM GameHistory")
    LiveData<List<GameHistory>> getAllLiveData();
}
