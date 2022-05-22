package com.example.monopoly.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.monopoly.data.converters.DateConverter;
import com.example.monopoly.data.converters.GameStateConverter;
import com.example.monopoly.data.converters.StringArrayConverter;


@TypeConverters(value = {DateConverter.class, StringArrayConverter.class, GameStateConverter.class})
@Database(entities = {Game.class, GameStateSnapshot.class}, version = 1, exportSchema = false)
public abstract class MonopolyDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
    public abstract GameStateSnapshotDao gameStateSnapshotDao();

    private static final String DATABASE_NAME = "monopoly.db";
    private static MonopolyDatabase instance = null;

    public static MonopolyDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MonopolyDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MonopolyDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}
