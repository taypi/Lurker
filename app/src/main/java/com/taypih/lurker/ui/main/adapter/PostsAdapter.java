package com.taypih.lurker.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.taypih.lurker.R;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.utils.ImageUtils;

import java.util.Objects;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(holder.postImage.getContext(), Objects.requireNonNull(getItem(position)));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ups;
        TextView title;
        TextView author;
        TextView subreddit;
        TextView numComments;
        ImageView postImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ups = itemView.findViewById(R.id.tv_upvotes);
            title = itemView.findViewById(R.id.tv_title);
            author = itemView.findViewById(R.id.tv_author);
            subreddit = itemView.findViewById(R.id.tv_subreddit);
            numComments = itemView.findViewById(R.id.tv_comments);
            postImage = itemView.findViewById(R.id.iv_post);

            itemView.setOnClickListener(v -> clickHandler.onClick(getItem(getAdapterPosition())));
        }

        @SuppressLint("DefaultLocale")
        void bind(Context context, Post post) {
            ups.setText(String.format("%d", post.getNumComments()));
            title.setText(post.getTitle());
            author.setText(String.format("Posted by u/%s", post.getAuthor()));
            subreddit.setText(post.getSubredditNamePrefixed());
            numComments.setText(String.format("%d", post.getNumComments()));

            ImageUtils.setImage(context, postImage, post);
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
