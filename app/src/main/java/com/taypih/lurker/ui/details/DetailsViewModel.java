package com.taypih.lurker.ui.details;

import android.annotation.SuppressLint;
import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.taypih.lurker.R;
import com.taypih.lurker.model.Comment;
import com.taypih.lurker.model.DetailResponse;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.repository.Repository;
import com.taypih.lurker.ui.ViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.taypih.lurker.ui.ViewState.ERROR;
import static com.taypih.lurker.ui.ViewState.LOADED;
import static com.taypih.lurker.ui.ViewState.LOADING;

@SuppressLint("CheckResult")
public class DetailsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Comment>> comments = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFavorite = new MutableLiveData<>(false);
    private MutableLiveData<ViewState> viewState = new MutableLiveData<>(LOADING);
    private Executor executor;
    private Repository repository;
    private Post post;
    private SimpleExoPlayer player;
    private long playbackPosition;
    private boolean playWhenReady;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        executor = Executors.newSingleThreadExecutor();
        playbackPosition = C.TIME_UNSET;
        playWhenReady = true;
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public LiveData<Boolean> isFavorite() {
        return isFavorite;
    }

    public LiveData<ViewState> getViewState() {
        return viewState;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }

    public boolean shouldPlayWhenReady() {
        return playWhenReady;
    }

    public void loadComments() {
        executor.execute(() ->
                repository.getPostDetails(post.getId()).subscribe(
                this::onCommentsLoaded,
                this::onError));
    }

    public void setInitialValues(long playbackPosition, boolean playWhenReady) {
        this.playbackPosition = playbackPosition;
        this.playWhenReady = playWhenReady;
    }

    public boolean initializePlayer(String videoUrl) {
        if (videoUrl == null) {
            return false;
        }
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getApplication());
            player.setPlayWhenReady(playWhenReady);

            boolean hasPlaybackPosition = playbackPosition != C.TIME_UNSET;
            if (hasPlaybackPosition) {
                player.seekTo(playbackPosition);
            }
            player.prepare(getVideoSource(videoUrl), !hasPlaybackPosition, false);
        }
        return true;
    }

    public void releasePlayer() {
        saveCurrentState();
        if (player != null) {
            player.stop(true);
            player.release();
            player = null;
        }
    }

    public void toggleFavoriteStatus() {
        boolean wasFavorite = isFavorite.getValue() != null && isFavorite.getValue();
        isFavorite.postValue(!wasFavorite);
        if (wasFavorite) {
            repository.deletePost(post);
        } else {
            repository.insertPost(post);
        }
    }

    public void loadFavorite() {
        executor.execute(() -> repository.findById(post.getId()).subscribe(
                favorite -> isFavorite.postValue(favorite != null)));
    }

    private MediaSource getVideoSource(String url) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplication(),
                Util.getUserAgent(getApplication(), getApplication().getString(R.string.app_name)));
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
    }

    private void saveCurrentState() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
        }
    }

    private void onCommentsLoaded(List<DetailResponse> response) {
        viewState.postValue(LOADED);
        comments.postValue(response.size() >= 1 ?
                response.get(1).getComments() : new ArrayList<>());
    }

    private void onError(Throwable throwable) {
        viewState.postValue(ERROR);
        throwable.printStackTrace();
    }
}
