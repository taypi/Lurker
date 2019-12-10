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
import com.taypih.lurker.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DetailsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    private SimpleExoPlayer player;
    private long playbackPosition;
    private boolean playWhenReady;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        playbackPosition = C.TIME_UNSET;
        playWhenReady = true;
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
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

    public boolean initializePlayer(String videoUrl) {
        if (videoUrl == null) {
            return false;
        }
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getApplication());
            player.setPlayWhenReady(playWhenReady);

            boolean hasPlaybackPosition = playbackPosition != C.INDEX_UNSET;
            if (hasPlaybackPosition) {
                player.seekTo(playbackPosition);
            }
            player.prepare(getVideoSource(videoUrl), !hasPlaybackPosition, false);
        }

        return true;
    }

    private MediaSource getVideoSource(String url) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplication(),
                Util.getUserAgent(getApplication(), getApplication().getString(R.string.app_name)));
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
    }

    public void releasePlayer() {
        if (player != null) {
            player.stop(true);
            player.release();
            player = null;
        }
    }
}
