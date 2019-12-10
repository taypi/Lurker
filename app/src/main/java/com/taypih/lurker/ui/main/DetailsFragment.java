package com.taypih.lurker.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.taypih.lurker.R;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.repository.Repository;
import com.taypih.lurker.ui.main.adapter.CommentsAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailsFragment extends Fragment {
    private static final String KEY_PLAYBACK_POSITION = "playback_position";
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private static final String KEY_URL= "url";

    private TextView ups;
    private TextView title;
    private TextView author;
    private TextView subreddit;
    private TextView numComments;
    private ImageView postImage;

    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    private long mPlaybackPosition;
    private boolean mPlayWhenReady;

    private String url;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        mPlayerView = view.findViewById(R.id.player);
        setInitialValues(savedInstanceState);

        return view;
    }

    private void setInitialValues(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(KEY_PLAYBACK_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            url = savedInstanceState.getString(KEY_URL);
        } else {
            mPlaybackPosition = C.TIME_UNSET;
            mPlayWhenReady = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PLAYBACK_POSITION, mPlaybackPosition);
        outState.putBoolean(KEY_PLAY_WHEN_READY, mPlayWhenReady);
    }

    private void initializePlayer() {
        if (url == null) {
            return;
        }
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            mPlayerView.setPlayer(mPlayer);
        }
        mPlayer.setPlayWhenReady(mPlayWhenReady);

        boolean hasPlaybackPosition = mPlaybackPosition != C.INDEX_UNSET;
        if (hasPlaybackPosition) {
            mPlayer.seekTo(mPlaybackPosition);
        }

        mPlayer.prepare(getVideoSource(url), !hasPlaybackPosition, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onStop() {
        saveCurrentState();
        releasePlayer();
        super.onStop();
    }

    private MediaSource getVideoSource(String url) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getActivity().getString(R.string.app_name)));
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
    }

    private void saveCurrentState() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop(true);
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ups = view.findViewById(R.id.tv_upvotes);
        title = view.findViewById(R.id.tv_title);
        author = view.findViewById(R.id.tv_author);
        subreddit = view.findViewById(R.id.tv_subreddit);
        numComments = view.findViewById(R.id.tv_comments);
        postImage = view.findViewById(R.id.iv_post);

        Post post = getArguments().getParcelable(Post.class.getSimpleName());

        if (post.getContent().isVideo()) {
            url = post.getContent().getMediaUrl();
        }
        ups.setText(String.format("%d", post.getNumComments()));
        title.setText(post.getTitle());
        author.setText(String.format("Posted by u/%s", post.getAuthor()));
        subreddit.setText(post.getSubredditNamePrefixed());
        numComments.setText(String.format("%d", post.getNumComments()));

//        ImageUtils.setImage(postImage, post);

        RecyclerView rv = view.findViewById(R.id.rv_comments);
        CommentsAdapter adapter = new CommentsAdapter();
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        Repository repository = Repository.getInstance();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            repository.getPostDetails(post.getId()).subscribe(response -> adapter.submitList(response.get(1).getComments()), Throwable::printStackTrace);
        });
    }
}
