package com.example.monopoly.data.converters;

import androidx.room.TypeConverter;

import com.example.monopoly.game.engine.GameEngine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameStateConverter {

    @TypeConverter
    public static GameEngine.GameState fromString(String value) {
        Type type = new TypeToken<GameEngine.GameState>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromGameState(GameEngine.GameState gameState) {
        Gson gson = new Gson();
        String json = gson.toJson(gameState);
        return json;
    }


}
