package com.taypih.lurker.ui.list;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taypih.lurker.R;
import com.taypih.lurker.databinding.MainFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.PostsAdapter;
import com.taypih.lurker.ui.details.DetailsFragment;
import com.taypih.lurker.ui.subreddits.SubredditsFragment;

import java.util.Objects;

public class ListFragment extends Fragment {

    private ListViewModel viewModel;
    private MainFragmentBinding binding;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        binding.toolbar .setOnClickListener(this::startSubredditsFragment);
    }

    /**
     * Setup recycler view and its adapter.
     */
    private void setupRecyclerView() {
        PostsAdapter adapter = new PostsAdapter(this::startDetailsFragment);
        RecyclerView recyclerView = binding.rvPosts;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        viewModel.getPagedListLiveData().observe(this, adapter::submitList);
    }

    /**
     * Replace current fragment by subreddits fragment.
     */
    private void startSubredditsFragment(View view) {
        Objects.requireNonNull(getFragmentManager())
                .beginTransaction()
                .replace(R.id.container, SubredditsFragment.newInstance(), SubredditsFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Replace current fragment by details fragment.
     *
     * @param post the Post passed to the other fragment.
     */
    private void startDetailsFragment(Post post) {
        DetailsFragment fragment = DetailsFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable(Post.class.getSimpleName(), post);
        fragment.setArguments(args);
        Objects.requireNonNull(getFragmentManager())
                .beginTransaction()
                .replace(R.id.container, fragment, DetailsFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }
}
