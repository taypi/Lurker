package com.taypih.lurker.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.taypih.lurker.R;
import com.taypih.lurker.databinding.DetailsFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.CommentsAdapter;
import com.taypih.lurker.widget.FavoriteWidgetService;

import java.util.Objects;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DetailsFragment extends Fragment {
    private static final String KEY_PLAYBACK_POSITION = "playback_position";
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private DetailsFragmentBinding binding;
    private DetailsViewModel viewModel;
    private PlayerView playerView;
    private String videoUrl;
    private Menu menu;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);

        viewModel.isFavorite().observe(this, this::updateFavoriteIcon);

        playerView = binding.layoutPost.player;
        setPlayerInitialValues(savedInstanceState);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.detailsToolbar);
        setHasOptionsMenu(true);
        setupAds();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onStop() {
        viewModel.releasePlayer();
        super.onStop();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Post post = Objects.requireNonNull(getArguments()).getParcelable(Post.class.getSimpleName());
        if (post == null) {
            return;
        }
        viewModel.setPost(post);
        viewModel.loadFavorite();
        viewModel.loadComments();

        setupRecyclerView();
        binding.setModel(post);
        binding.executePendingBindings();

        if (post.hasVideo()) {
            videoUrl = post.getMediaUrl();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.details, menu);
        updateFavoriteIcon(viewModel.isFavorite().getValue());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Objects.requireNonNull(getActivity()).onBackPressed();
                return true;
            case R.id.menu_favorite:
                viewModel.toggleFavoriteStatus();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PLAYBACK_POSITION, viewModel.getPlaybackPosition());
        outState.putBoolean(KEY_PLAY_WHEN_READY, viewModel.shouldPlayWhenReady());
    }

    /**
     * Setup recycler view and its adapter.
     */
    private void setupRecyclerView() {
        CommentsAdapter commentsAdapter = new CommentsAdapter();
        RecyclerView recyclerView = binding.rvComments;
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        viewModel.getComments().observe(this, commentsAdapter::submitList);
    }

    /**
     * Get player initial values from saved instance, so we can recreate its state
     *
     * @param savedInstanceState the instance where the previous state was saved
     */
    private void setPlayerInitialValues(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            viewModel.setInitialValues(savedInstanceState.getLong(KEY_PLAYBACK_POSITION),
                                        savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY));
        }
    }

    /**
     * Initialize player and its view if there is a video url.
     */
    private void initializePlayer() {
        if (viewModel.initializePlayer(videoUrl)) {
            playerView.setPlayer(viewModel.getPlayer());
            binding.executePendingBindings();
            binding.layoutPost.player.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.video_height)));
        }
    }

    /**
     * Update favorite icon according to the current post favorite status.
     *
     * @param isFavorite true if the icon should be filled, false otherwise.
     */
    private void updateFavoriteIcon(Boolean isFavorite) {
        if (menu != null) {
            int iconId = isFavorite ? R.drawable.ic_favorite_fill : R.drawable.ic_favorite;
            menu.findItem(R.id.menu_favorite).setIcon(iconId);
        }
        Objects.requireNonNull(getActivity()).startService(new Intent(getContext(), FavoriteWidgetService.class));
    }

    private void setupAds() {
        AdView adView = binding.adView;
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }
}
