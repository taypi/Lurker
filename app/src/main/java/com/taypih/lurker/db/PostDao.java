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
    @Query("SELECT * FROM posts")
    Observable<List<Post>> loadAll();

    @Insert
    void insert(Post post);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT COUNT(*) from posts")
    long count();
}
