package com.taypih.lurker.ui.list;

import android.app.ActionBar;
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
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taypih.lurker.R;
import com.taypih.lurker.databinding.ListFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.PostsAdapter;
import com.taypih.lurker.ui.details.DetailsFragment;

import java.util.Objects;

public class ListFragment extends Fragment {

    private ListViewModel viewModel;
    private ListFragmentBinding binding;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular:
                setToolbarTitle(R.string.popular);
                return true;
            case R.id.menu_favorite:
                setToolbarTitle(R.string.favorites);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setToolbarTitle(int titleResId) {
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity()))
                .getSupportActionBar()).setTitle(titleResId);
    }
}
