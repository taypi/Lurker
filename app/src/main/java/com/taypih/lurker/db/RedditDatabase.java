package com.taypih.lurker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taypih.lurker.model.Post;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class RedditDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "lurker.db";
    private static RedditDatabase sInstance;

    synchronized public static RedditDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context,
                    RedditDatabase.class, DATABASE_NAME)
                    .build();
        }
        return sInstance;
    }

    public abstract PostDao postDao();
}
