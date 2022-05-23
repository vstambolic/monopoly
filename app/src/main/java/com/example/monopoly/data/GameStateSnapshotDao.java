package com.example.monopoly.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.monopoly.game.engine.GameEngine;


@Dao
public interface GameStateSnapshotDao {
    @Insert
    long insert(GameStateSnapshot gameStateSnapshot);

    @Query("SELECT GameState FROM GameStateSnapshot WHERE gameId = :gameId and `index` = :index")
    GameEngine.GameState getGameState(long gameId, long index);
}
