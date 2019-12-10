package com.taypih.lurker.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.taypih.lurker.databinding.ItemSubredditBinding;
import com.taypih.lurker.model.Subreddit;

public class SubredditsAdapter extends ListAdapter<Subreddit, ViewHolder> {
    public SubredditsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSubredditBinding binding = ItemSubredditBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static final DiffUtil.ItemCallback<Subreddit> DIFF_CALLBACK = new DiffUtil.ItemCallback<Subreddit>() {

        @Override
        public boolean areItemsTheSame(@NonNull Subreddit oldItem, @NonNull Subreddit newItem) {
            return oldItem.getDisplayNamePrefixed().equals(newItem.getDisplayNamePrefixed());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Subreddit oldItem, @NonNull Subreddit newItem) {
            return oldItem.getDisplayNamePrefixed().equals(newItem.getDisplayNamePrefixed());
        }
    };
}
