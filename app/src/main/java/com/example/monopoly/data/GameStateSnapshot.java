package com.example.monopoly.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.monopoly.game.engine.GameEngine;

@Entity(foreignKeys = {@ForeignKey(
                entity = Game.class,
                parentColumns = "id",
                childColumns = "gameId",
                onDelete = ForeignKey.CASCADE)
}, primaryKeys = {"gameId","index"})
public class GameStateSnapshot {

    private long gameId;
    private long index;
    private GameEngine.GameState gameState;

    public GameStateSnapshot(long gameId, long index, GameEngine.GameState gameState) {
        this.gameId = gameId;
        this.index = index;
        this.gameState = gameState;
    }


    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public GameEngine.GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameEngine.GameState gameState) {
        this.gameState = gameState;
    }
}
