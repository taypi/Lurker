package com.taypih.lurker.ui.posts;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.taypih.lurker.R;
import com.taypih.lurker.databinding.PostsFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.PostsAdapter;
import com.taypih.lurker.ui.details.DetailsFragment;

import java.util.Objects;

public class PostsFragment extends Fragment {

    private PostsViewModel viewModel;
    private PostsFragmentBinding binding;
    private RecyclerView recyclerView;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.posts_fragment, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);

        setupRecyclerView();

        viewModel.getUpdateViewEvent().observe(getViewLifecycleOwner(), this::updateView);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular:
                viewModel.setDataSource(true);
                return true;
            case R.id.menu_favorites:
                viewModel.setDataSource(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Setup the recycler view with a new adapter
     */
    private void setupRecyclerView() {
        recyclerView = binding.rvPosts;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        resetAdapter();
    }

    private void resetAdapter() {
        PostsAdapter adapter = new PostsAdapter(this::startDetailsFragment);
        recyclerView.setAdapter(adapter);
        viewModel.gePostList().removeObservers(getViewLifecycleOwner());
        viewModel.gePostList().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private void updateView(boolean loadFromApi) {
        resetAdapter();
        setToolbarTitle(loadFromApi ? R.string.popular : R.string.favorites);
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

    /**
     * Update toolbar title
     *
     * @param titleResId resource id from title
     */
    private void setToolbarTitle(int titleResId) {
        binding.toolbar.setTitle(getString(titleResId));
    }
}
