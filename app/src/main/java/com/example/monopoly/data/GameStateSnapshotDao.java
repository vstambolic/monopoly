package com.example.monopoly.data;

import androidx.room.Dao;
import androidx.room.Insert;


@Dao
public interface GameStateSnapshotDao {
    @Insert
    long insert(GameStateSnapshot gameStateSnapshot);
}
