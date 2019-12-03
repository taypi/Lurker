package com.taypih.lurker.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.taypih.lurker.model.Post;
import com.taypih.lurker.repository.RedditDataSourceFactory;
import com.taypih.lurker.repository.Repository;

public class MainViewModel extends AndroidViewModel {
    private RedditDataSourceFactory dataSourceFactory;
    private LiveData<PagedList<Post>> pagedListLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataSourceFactory = new RedditDataSourceFactory(Repository.getInstance());
        initializePaging();
    }

    public LiveData<PagedList<Post>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    private void initializePaging() {
        PagedList.Config config =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(3)
                        .setPageSize(3).build();

        pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config).build();
    }
}
