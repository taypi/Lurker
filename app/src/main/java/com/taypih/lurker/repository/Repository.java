package com.taypih.lurker.repository;

import android.content.Context;

import com.taypih.lurker.api.RedditApi;
import com.taypih.lurker.db.RedditDatabase;
import com.taypih.lurker.model.DetailResponse;
import com.taypih.lurker.model.ListResponse;
import com.taypih.lurker.model.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://www.reddit.com";
    private static Repository instance;
    private RedditDatabase database;
    private RedditApi apiService;

    private Repository(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(RedditApi.class);
        database = RedditDatabase.getInstance(context);
    }

    synchronized public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public Observable<ListResponse> getTop(int limit) {
        return apiService.getTop(limit);
    }

    public Observable<ListResponse> getTopAfter(int limit, String after) {
        return apiService.getTopAfter(limit, after);
    }

    public Observable<ListResponse> getTopBefore(int limit, String before) {
        return apiService.getTopBefore(limit, before);
    }

    public Observable<List<DetailResponse>> getPostDetails(String id) {
        return apiService.getPostDetails(id);
    }

    public void insertPost(Post post) {
        database.postDao().insert(post);
    }

    public void deletePost(Post post) {
        database.postDao().delete(post);
    }

    public Observable<Post> findById(String id) {
        return database.postDao().findById(id);
    }
}
