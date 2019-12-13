package com.taypih.lurker.paging;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.taypih.lurker.model.Post;
import com.taypih.lurker.repository.Repository;

@SuppressLint("CheckResult")
public class PageKeyedRedditDataSource extends PageKeyedDataSource<String, Post> {
    Repository repository;

    PageKeyedRedditDataSource(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params,
                            @NonNull LoadInitialCallback<String, Post> callback) {
        repository.getTop(params.requestedLoadSize)
                .subscribe(response -> callback.onResult(response.getPosts(), response.getBefore(), response.getAfter()));

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params,
                           @NonNull LoadCallback<String, Post> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params,
                          @NonNull LoadCallback<String, Post> callback) {
        repository.getTopAfter(params.requestedLoadSize, params.key)
                .subscribe(response -> callback.onResult(response.getPosts(), response.getAfter()));
    }


}
