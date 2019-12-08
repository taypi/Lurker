package com.taypih.lurker.ui.main;

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
import com.taypih.lurker.model.Post;
import com.taypih.lurker.ui.main.adapter.PostsAdapter;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        RecyclerView rv = view.findViewById(R.id.rv_posts);
        PostsAdapter adapter = new PostsAdapter(post -> {
            DetailsFragment fragment = DetailsFragment.newInstance();
            Bundle args = new Bundle();
            args.putParcelable(Post.class.getSimpleName(), post);
            fragment.setArguments(args);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, DetailsFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        });
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        viewModel.getPagedListLiveData().observe(this, adapter::submitList);
    }
}
