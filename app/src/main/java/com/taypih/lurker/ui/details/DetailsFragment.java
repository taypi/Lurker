package com.taypih.lurker.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.taypih.lurker.R;
import com.taypih.lurker.databinding.DetailsFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.CommentsAdapter;

import java.util.Objects;

public class DetailsFragment extends Fragment {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        playerView = binding.layoutPost.player;
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.detailsToolbar);
        setHasOptionsMenu(true);

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
        setupRecyclerView();

        viewModel.isFavorite().observe(this, isFavorite -> {
            if (menu != null) {
                menu.findItem(R.id.menu_favorite).setIcon(isFavorite ? R.drawable.ic_favorite_fill : R.drawable.ic_favorite);
            }
        });

        viewModel.loadComments();
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
        menu.findItem(R.id.menu_favorite).setIcon(viewModel.isFavorite().getValue() ? R.drawable.ic_favorite_fill : R.drawable.ic_favorite);
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
     * Initialize player and its view if there is a video url.
     */
    private void initializePlayer() {
        if (viewModel.initializePlayer(videoUrl)) {
            playerView.setPlayer(viewModel.getPlayer());
            binding.executePendingBindings();
        }
    }
}
