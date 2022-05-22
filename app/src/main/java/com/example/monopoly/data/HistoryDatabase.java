package com.example.monopoly.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.monopoly.data.converters.DateConverter;
import com.example.monopoly.data.converters.StringArrayConverter;


@TypeConverters(value = {DateConverter.class, StringArrayConverter.class})
@Database(entities = {GameHistory.class}, version = 1, exportSchema = false)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract GameHistoryDao gameHistoryDao();

    private static final String DATABASE_NAME = "monopoly.db";
    private static HistoryDatabase instance = null;

    public static HistoryDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (HistoryDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            HistoryDatabase.class,
                            DATABASE_NAME)
                            .allowMainThreadQueries() // TODO
                            .build();
                }
            }
        }
        return instance;
    }
}
