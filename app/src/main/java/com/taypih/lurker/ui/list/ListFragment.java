package com.taypih.lurker.ui.list;

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
import com.taypih.lurker.databinding.ListFragmentBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.adapter.PostsAdapter;
import com.taypih.lurker.ui.details.DetailsFragment;

import java.util.Objects;

public class ListFragment extends Fragment {

    private ListViewModel viewModel;
    private ListFragmentBinding binding;
    private RecyclerView recyclerView;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        loadListAccordingToPreference();

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        cleanObservers();
        switch (item.getItemId()) {
            case R.id.menu_popular:
                setToolbarTitle(R.string.popular);
                viewModel.getApiListLiveData().observe(getViewLifecycleOwner(),
                        setupRecyclerView()::submitList);
                savePreference(true);
                return true;
            case R.id.menu_favorites:
                setToolbarTitle(R.string.favorites);
                viewModel.getDbListLiveData().observe(getViewLifecycleOwner(),
                        setupRecyclerView()::submitList);
                savePreference(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Setup the recycler view with a new adapter
     *
     * @return the recycler view adapter
     */
    private PostsAdapter setupRecyclerView() {
        PostsAdapter adapter = new PostsAdapter(this::startDetailsFragment);
        recyclerView = binding.rvPosts;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        return adapter;
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

    /**
     * Remove all observers from the live datas, since new observers are being added when
     * an option item is selected.
     */
    private void cleanObservers() {
        viewModel.getApiListLiveData().removeObservers(getViewLifecycleOwner());
        viewModel.getDbListLiveData().removeObservers(getViewLifecycleOwner());
    }

    private void loadListAccordingToPreference() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean getFromApi = sharedPref.getBoolean(getString(R.string.pref_type), true);
        if (getFromApi) {
            setToolbarTitle(R.string.popular);
            viewModel.getApiListLiveData().observe(getViewLifecycleOwner(),
                    setupRecyclerView()::submitList);
        } else {
            setToolbarTitle(R.string.favorite);
            viewModel.getDbListLiveData().observe(getViewLifecycleOwner(),
                    setupRecyclerView()::submitList);
        }
    }

    private void savePreference(boolean getFromApi) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.pref_type), getFromApi);
        editor.apply();
    }
}
