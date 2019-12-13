package com.taypih.lurker.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.taypih.lurker.model.Post;

import io.reactivex.Observable;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM posts")
    DataSource.Factory<Integer, Post> loadAll();

    @Query("SELECT COUNT(*) from posts")
    long count();

    @Query("SELECT * FROM posts where id = :id LIMIT 1")
    Observable<Post> findById(String id);
}
