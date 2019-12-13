package com.taypih.lurker.ui.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.taypih.lurker.model.Post;
import com.taypih.lurker.paging.RedditDataSourceFactory;
import com.taypih.lurker.repository.Repository;

public class ListViewModel extends AndroidViewModel {
    private Repository repository;
    private RedditDataSourceFactory dataSourceFactory;
    private LiveData<PagedList<Post>> apiListLiveData;
    private LiveData<PagedList<Post>> dbListLiveData;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        dataSourceFactory = new RedditDataSourceFactory(Repository.getInstance(getApplication()));
        initializePaging();
    }

    public LiveData<PagedList<Post>> getApiListLiveData() {
        return apiListLiveData;
    }

    public LiveData<PagedList<Post>> getDbListLiveData() {
        return dbListLiveData;
    }

    private void initializePaging() {
        PagedList.Config config =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(5)
                        .setPageSize(20).build();

        apiListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config).build();
        dbListLiveData = new LivePagedListBuilder<>(repository.loadAllFromDb(), config).build();
    }
}
