package com.taypih.lurker.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.taypih.lurker.api.RedditApiService;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.model.ApiResponse;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://www.reddit.com";
    private static Repository instance;
    private RedditApiService apiService;

    private Repository(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(RedditApiService.class);
    }

    synchronized public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public Observable<ApiResponse> requestPosts() {
        return apiService.getResponse();
    }
}
