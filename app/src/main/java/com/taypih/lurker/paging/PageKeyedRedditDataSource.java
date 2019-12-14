package com.taypih.lurker.paging;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.taypih.lurker.model.Post;
import com.taypih.lurker.repository.RequestState;
import com.taypih.lurker.repository.Repository;

import static com.taypih.lurker.repository.RequestState.ERROR;
import static com.taypih.lurker.repository.RequestState.LOADED;
import static com.taypih.lurker.repository.RequestState.LOADING;

@SuppressLint("CheckResult")
public class PageKeyedRedditDataSource extends PageKeyedDataSource<String, Post> {
    private MutableLiveData<RequestState> requestState = new MutableLiveData<>();
    private Repository repository;

    PageKeyedRedditDataSource(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params,
                            @NonNull LoadInitialCallback<String, Post> callback) {
        requestState.postValue(LOADING);
        repository.getTop(params.requestedLoadSize).subscribe(response -> {
            requestState.postValue(LOADED);
            callback.onResult(response.getPosts(), response.getBefore(), response.getAfter());
        }, throwable -> requestState.postValue(ERROR));

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params,
                           @NonNull LoadCallback<String, Post> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params,
                          @NonNull LoadCallback<String, Post> callback) {
        repository.getTopAfter(params.requestedLoadSize, params.key).subscribe(response -> {
            requestState.postValue(LOADED);
            callback.onResult(response.getPosts(), response.getAfter());
        }, throwable -> requestState.postValue(LOADED));
    }

    public LiveData<RequestState> getRequestState() {
        return requestState;
    }
}
