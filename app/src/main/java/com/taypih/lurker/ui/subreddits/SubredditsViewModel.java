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
import java.util.concurrent.Executors;

public class SubredditsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Subreddit>> subreddits = new MutableLiveData<>();

    public SubredditsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Subreddit>> getSubreddits() {
        return subreddits;
    }

    @SuppressLint("CheckResult")
    public void loadSubreddits() {
        Executors.newSingleThreadExecutor().submit(() -> {
            Repository.getInstance().getSubreddits().subscribe(
                    response -> subreddits.postValue(response.getSubreddits()),
                    Throwable::printStackTrace);
        });
    }
}
