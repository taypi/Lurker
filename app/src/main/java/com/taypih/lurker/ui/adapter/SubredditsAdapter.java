package com.taypih.lurker.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.taypih.lurker.databinding.ItemSubredditBinding;
import com.taypih.lurker.model.Subreddit;

public class SubredditsAdapter extends ListAdapter<Subreddit, SubredditsAdapter.ViewHolder> {
    private final SubredditOnClickHandler clickHandler;

    public SubredditsAdapter(SubredditOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.clickHandler = clickHandler;
    }

    public interface SubredditOnClickHandler {
        void onClick(Subreddit subreddit);
    }

    @NonNull
    @Override
    public SubredditsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSubredditBinding binding = ItemSubredditBinding.inflate(layoutInflater, parent, false);
        return new SubredditsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubredditsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubredditBinding binding;

        public ViewHolder(ItemSubredditBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> clickHandler.onClick(getItem(getAdapterPosition())));
        }

        public void bind(Subreddit model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
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
