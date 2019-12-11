package com.taypih.lurker.repository;

import android.content.Context;

import com.taypih.lurker.api.RedditApi;
import com.taypih.lurker.db.SubredditDatabase;
import com.taypih.lurker.model.DetailResponse;
import com.taypih.lurker.model.ListResponse;
import com.taypih.lurker.model.Subreddit;
import com.taypih.lurker.model.SubredditResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://www.reddit.com";
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private static Repository instance;
    private SubredditDatabase database;
    private RedditApi apiService;

    private Repository(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(RedditApi.class);
        database = SubredditDatabase.getInstance(context);
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

    public Observable<SubredditResponse> getSubreddits() {
        return apiService.getSubreddits(50);
    }

    public Observable<List<Subreddit>> findSubreddits() {
        return database.subredditDao().findSubreddits();
    }

    public void setFavoriteSubreddit(Subreddit subreddit) {
        database.subredditDao().insertSubreddit(subreddit);
    }
}
