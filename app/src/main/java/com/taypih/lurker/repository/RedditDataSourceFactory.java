package com.taypih.lurker.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.taypih.lurker.model.Post;

public class RedditDataSourceFactory extends DataSource.Factory<String, Post> {
    private MutableLiveData<PageKeyedRedditDataSource> sourceLiveData;
    private Repository repository;

    public RedditDataSourceFactory(Repository repository) {
        this.repository = repository;
        sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<String, Post> create() {
        PageKeyedRedditDataSource source = new PageKeyedRedditDataSource(repository);
        sourceLiveData.postValue(source);
        return source;
    }
}
