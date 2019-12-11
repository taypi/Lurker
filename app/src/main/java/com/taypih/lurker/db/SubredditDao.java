package com.taypih.lurker.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.taypih.lurker.model.Subreddit;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface SubredditDao {
    @Query("SELECT * FROM subreddits")
    Observable<List<Subreddit>> findSubreddits();

    @Insert
    void insertSubreddit(Subreddit subreddit);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSubreddit(Subreddit subreddit);

    @Delete
    void deleteSubreddit(Subreddit subreddit);

    @Query("SELECT COUNT(*) from subreddits")
    long count();
}
