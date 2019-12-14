package com.taypih.lurker.ui.posts;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.taypih.lurker.model.Post;
import com.taypih.lurker.paging.PageKeyedRedditDataSource;
import com.taypih.lurker.paging.RedditDataSourceFactory;
import com.taypih.lurker.repository.Repository;
import com.taypih.lurker.repository.RequestState;
import com.taypih.lurker.ui.ViewState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taypih.lurker.ui.ViewState.*;

public class PostsViewModel extends AndroidViewModel {
    private static final String KEY_PREF = "PREF_DATA_SOURCE";
    private static final Map<RequestState, ViewState> stateMap = new HashMap<>();

    private Repository repository;
    private boolean loadFromApi;

    private RedditDataSourceFactory dataSourceFactory;
    private MediatorLiveData<ViewState> viewState = new MediatorLiveData<>();
    private MediatorLiveData<PagedList<Post>> postList = new MediatorLiveData<>();
    private LiveData<RequestState> requestState;
    private LiveData<PagedList<Post>> apiList;
    private LiveData<PagedList<Post>> dbList;

    static {
        stateMap.put(RequestState.ERROR, ERROR);
        stateMap.put(RequestState.LOADED, LOADED);
        stateMap.put(RequestState.LOADING, LOADING);
    }

    public PostsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        loadFromApi = getPreference();
        dataSourceFactory = new RedditDataSourceFactory(Repository.getInstance(getApplication()));
        initializePaging();
        setDataSource(getPreference());

        requestState = Transformations.switchMap(dataSourceFactory.getSourceLiveData(),
                PageKeyedRedditDataSource::getRequestState);
        viewState.addSource(requestState, this::onDataSourceChanged);
        viewState.addSource(dbList, this::onDbSourceChanged);
    }

    public LiveData<PagedList<Post>> gePostList() {
        return postList;
    }

    public LiveData<ViewState> getViewState() {
        return viewState;
    }

    public void setDataSource(boolean isFromApi) {
        if (loadFromApi == isFromApi) return;
        loadFromApi = isFromApi;
        savePreference();
        if (loadFromApi) {
            postList.removeSource(dbList);
            postList.addSource(apiList, a -> {
                postList.setValue(a);
                onDataSourceChanged(requestState.getValue());
            });
        } else {
            postList.removeSource(apiList);
            postList.addSource(dbList, a -> {
                postList.postValue(a);
                onDbSourceChanged(a);
            });
        }
    }

    private void initializePaging() {
        PagedList.Config config =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        apiList = new LivePagedListBuilder<>(dataSourceFactory, config).build();
        dbList = new LivePagedListBuilder<>(repository.loadAllFromDb(), config).build();
    }

    private void onDataSourceChanged(RequestState requestState) {
        if (!loadFromApi) {
            viewState.postValue(stateMap.get(requestState));
        }
    }

    private void onDbSourceChanged(List<Post> posts) {
        viewState.postValue(posts.isEmpty() ? EMPTY : LOADED);
    }

    private boolean getPreference() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(KEY_PREF, true);
    }

    private void savePreference() {
        SharedPreferences sharedPref = getApplication()
                .getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_PREF, loadFromApi);
        editor.apply();
    }
}
