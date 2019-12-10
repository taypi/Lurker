package com.taypih.lurker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taypih.lurker.model.Subreddit;

@Database(entities = {Subreddit.class}, version = 1, exportSchema = false)
public abstract class SubredditDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "subreddits";
    private static SubredditDatabase sInstance;

    synchronized public static SubredditDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context,
                    SubredditDatabase.class, DATABASE_NAME).build();
        }
        return sInstance;
    }

    public abstract SubredditDao subredditDao();
}
