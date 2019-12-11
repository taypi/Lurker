package com.taypih.lurker.ui.subreddits;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.taypih.lurker.model.Subreddit;
import com.taypih.lurker.repository.Repository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressLint("CheckResult")
public class SubredditsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Subreddit>> subreddits = new MutableLiveData<>();
    private MutableLiveData<List<Subreddit>> favoriteSubreddits = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Repository repository;

    public SubredditsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public LiveData<List<Subreddit>> getSubreddits() {
        return subreddits;
    }

    public LiveData<List<Subreddit>> getFavoriteSubreddits() {
        return favoriteSubreddits;
    }

    public void loadSubreddits() {
        executor.execute(() ->
                repository.getSubreddits().subscribe(
                    response -> subreddits.postValue(response.getSubreddits()),
                    Throwable::printStackTrace));
    }

    public void loadFavorites() {
        executor.execute(() ->
                repository.findSubreddits().subscribe(
                        response ->  favoriteSubreddits.postValue(response),
                        Throwable::printStackTrace));
    }

    public void setFavorite(Subreddit subreddit) {
        executor.execute(() -> repository.setFavoriteSubreddit(subreddit));
    }
}
