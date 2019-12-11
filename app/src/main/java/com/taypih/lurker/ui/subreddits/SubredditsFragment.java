package com.taypih.lurker.ui.subreddits;

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

import com.taypih.lurker.R;
import com.taypih.lurker.databinding.SubredditsFragmentBinding;
import com.taypih.lurker.ui.adapter.SubredditsAdapter;

public class SubredditsFragment extends Fragment {
    private SubredditsFragmentBinding binding;
    private SubredditsViewModel viewModel;

    public static SubredditsFragment newInstance() {
        return new SubredditsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.subreddits_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(SubredditsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        viewModel.loadSubreddits();
    }

    /**
     * Setup recycler view and its adapter.
     */
    private void setupRecyclerView() {
        SubredditsAdapter adapter = new SubredditsAdapter(viewModel::setFavorite);
        RecyclerView recyclerView = binding.rvSubreddits;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        viewModel.getSubreddits().observe(this, adapter::submitList);
//        viewModel.getFavoriteSubreddits().observe(this, adapter::submitList);

    }
}
