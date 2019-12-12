package com.taypih.lurker.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.taypih.lurker.model.Post;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface PostDao {
    @Insert
    void insert(Post post);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM posts")
    Observable<List<Post>> loadAll();

    @Query("SELECT COUNT(*) from posts")
    long count();

    @Query("SELECT * FROM posts where id = :id LIMIT 1")
    Observable<Post> findById(String id);
}
