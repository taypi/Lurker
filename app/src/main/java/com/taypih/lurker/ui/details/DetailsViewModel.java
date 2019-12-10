package com.taypih.lurker.ui.details;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.taypih.lurker.model.Comment;
import com.taypih.lurker.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DetailsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    @SuppressLint("CheckResult")
    public void loadComments(String postId) {
        Executors.newSingleThreadExecutor().submit(() -> {
            Repository.getInstance().getPostDetails(postId).subscribe(
                    response -> comments.postValue(response.size() >= 1 ?
                            response.get(1).getComments() : new ArrayList<>()),
                    Throwable::printStackTrace);
        });
    }
}
