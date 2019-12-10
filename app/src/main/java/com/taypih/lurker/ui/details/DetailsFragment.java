package com.taypih.lurker.ui.details;

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

import com.google.android.exoplayer2.ui.PlayerView;
import com.taypih.lurker.R;
import com.taypih.lurker.databinding.DetailsFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.CommentsAdapter;

import java.util.Objects;

public class DetailsFragment extends Fragment {
    private DetailsFragmentBinding binding;
    private DetailsViewModel viewModel;
    private CommentsAdapter commentsAdapter;
    private PlayerView playerView;
    private String videoUrl;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        commentsAdapter = new CommentsAdapter();
        playerView = binding.layoutPost.player;

        viewModel.getComments().observe(this, commentsAdapter::submitList);

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
        setupRecyclerView();
        Post post = Objects.requireNonNull(getArguments()).getParcelable(Post.class.getSimpleName());
        if (post == null) {
            return;
        }

        viewModel.loadComments(post.getId());
        binding.setModel(post);
        binding.executePendingBindings();

        if (post.getContent().isVideo()) {
            videoUrl = post.getContent().getMediaUrl();
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

    /**
     * Initialize player and its view if there is a video url.
     */
    private void initializePlayer() {
        if (viewModel.initializePlayer(videoUrl)) {
            playerView.setPlayer(viewModel.getPlayer());
        }
    }
}
