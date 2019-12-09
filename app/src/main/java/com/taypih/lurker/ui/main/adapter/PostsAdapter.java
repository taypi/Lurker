package com.taypih.lurker.ui.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.taypih.lurker.R;
import com.taypih.lurker.databinding.ItemPostBinding;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.utils.ImageUtils;

public class PostsAdapter extends PagedListAdapter<Post, PostsAdapter.ViewHolder> {
    private final PostOnClickHandler clickHandler;

    public PostsAdapter(PostOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.clickHandler = clickHandler;
    }

    public interface PostOnClickHandler {
        void onClick(Post post);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPostBinding binding = ItemPostBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;
        ImageView postImage;

        ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            postImage = itemView.findViewById(R.id.iv_post);

            itemView.setOnClickListener(v -> clickHandler.onClick(getItem(getAdapterPosition())));
        }

        void bind(Post post) {
            binding.setModel(post);
            binding.executePendingBindings();
            //ImageUtils.setImage(postImage, post);
        }
    }

    static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {

        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };
}
