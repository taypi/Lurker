package com.taypih.lurker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taypih.lurker.model.Subreddit;

import java.util.concurrent.Executors;

@Database(entities = {Subreddit.class}, version = 1, exportSchema = false)
public abstract class SubredditDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "lurker.db";
    private static SubredditDatabase sInstance;

    synchronized public static SubredditDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context,
                    SubredditDatabase.class, DATABASE_NAME)
                    .build();
            sInstance.populateInitialData();
        }
        return sInstance;
    }

    public abstract SubredditDao subredditDao();

    /**
     * Inserts data into the database if it is currently empty.
     */
    private void populateInitialData() {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            if (subredditDao().count() == 0) {
                        runInTransaction(() -> subredditDao().insertSubreddit(
                                new Subreddit("All",
                                        "https://a.thumbs.redditmedia.com/E0Bkwgwe5TkVLflBA7WMe9fMSC7DV2UOeff-UpNJeb0.png",
                                        "r/All")));
            }
        });
    }
}