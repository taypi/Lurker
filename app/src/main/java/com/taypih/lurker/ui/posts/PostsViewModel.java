package com.taypih.lurker.ui.posts;

import android.app.Application;

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
import com.taypih.lurker.ui.SingleLiveEvent;
import com.taypih.lurker.ui.ViewState;
import com.taypih.lurker.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taypih.lurker.ui.ViewState.*;

public class PostsViewModel extends AndroidViewModel {
    private static final Map<RequestState, ViewState> stateMap = new HashMap<>();

    RedditDataSourceFactory dataSourceFactory;
    private Repository repository;
    private Boolean loadFromApi;

    private MediatorLiveData<ViewState> viewState = new MediatorLiveData<>();
    private LiveData<PagedList<Post>> postList = new MediatorLiveData<>();
    private SingleLiveEvent<Boolean> updateView = new SingleLiveEvent<>();
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
        initializePaging();
        setDataSource(PreferenceUtils.getListTypePref(getApplication()));

        viewState.addSource(requestState, this::onApiSourceChanged);
        viewState.addSource(dbList, this::onDbSourceChanged);
        updateView.setValue(loadFromApi);
    }

    public LiveData<PagedList<Post>> gePostList() {
        return postList;
    }

    public LiveData<ViewState> getViewState() {
        return viewState;
    }

    public LiveData<Boolean> getUpdateViewEvent() {
        return updateView;
    }

    public void setDataSource(boolean isFromApi) {
        if (loadFromApi != null && loadFromApi == isFromApi) return;
        savePreference(isFromApi);
        updateView();
    }

    public void updateView() {
        if (loadFromApi) {
            if (dataSourceFactory.getSourceLiveData().getValue() != null) {
                dataSourceFactory.getSourceLiveData().getValue().invalidate();
            }
            postList = apiList;
            onApiSourceChanged(requestState.getValue());
        } else {
            postList = dbList;
            onDbSourceChanged(dbList.getValue());
        }
        updateView.setValue(loadFromApi);
    }

    private void initializePaging() {
        dataSourceFactory = new RedditDataSourceFactory(repository);
        requestState = Transformations.switchMap(dataSourceFactory.getSourceLiveData(),
                PageKeyedRedditDataSource::getRequestState);
        PagedList.Config config =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        apiList = new LivePagedListBuilder<>(dataSourceFactory, config).build();
        dbList = new LivePagedListBuilder<>(repository.loadAllFromDb(), config).build();
    }

    private void onApiSourceChanged(RequestState requestState) {
        if (loadFromApi) {
            viewState.setValue(stateMap.get(requestState));
        }
    }

    private void onDbSourceChanged(List<Post> posts) {
        if (!loadFromApi) {
            viewState.postValue(posts == null || posts.isEmpty() ? EMPTY : LOADED);
        }
    }

    private void savePreference(boolean isFromApi) {
        loadFromApi = isFromApi;
        PreferenceUtils.saveListTypePref(getApplication(), isFromApi);
    }
}
