package com.taypih.lurker.ui.details;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import com.taypih.lurker.databinding.DetailsFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.CommentsAdapter;

import java.util.Objects;

public class DetailsFragment extends Fragment {
    private static final String KEY_PLAYBACK_POSITION = "playback_position";
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private static final String KEY_URL= "url";

    private DetailsFragmentBinding binding;
    private DetailsViewModel viewModel;
    private CommentsAdapter commentsAdapter;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);

        commentsAdapter = new CommentsAdapter();

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        viewModel.getComments().observe(this, commentsAdapter::submitList);

        mPlayerView = binding.layoutPost.player;
        setInitialValues(savedInstanceState);

        return binding.getRoot();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        Post post = Objects.requireNonNull(getArguments()).getParcelable(Post.class.getSimpleName());
        if (post == null) {
            return;
        }

        viewModel.loadComments(post.getId());

        binding.setModel(post);
        binding.executePendingBindings();

        if (post.getContent().isVideo()) {
            url = post.getContent().getMediaUrl();
        }
    }

    /**
     * Setup recycler view and its adapter.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rvComments;
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
