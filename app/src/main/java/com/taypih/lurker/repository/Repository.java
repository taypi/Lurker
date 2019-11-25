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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://www.reddit.com";
    private static Repository sInstance;
    private RedditApiService mService;

    private Repository(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(RedditApiService.class);
    }

    synchronized public static Repository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Repository(context);
        }
        return sInstance;
    }

    public void requestPosts(final Consumer<List<Post>> onResponse) {
        Call<ApiResponse> call = mService.getResponse();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.body() != null) {
                    onResponse.accept(response.body().getPosts());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Request Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
