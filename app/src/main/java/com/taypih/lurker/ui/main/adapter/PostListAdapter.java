package com.taypih.lurker.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.taypih.lurker.R;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.utils.ImageUtils;

public class PostListAdapter extends ListAdapter<Post, PostListAdapter.ViewHolder> {
    public PostListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.ViewHolder holder, int position) {
        holder.bind(holder.mPostImage.getContext(), getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mUps;
        TextView mTitle;
        TextView mAuthor;
        TextView mSubreddit;
        TextView mNumComments;
        ImageView mPostImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUps = itemView.findViewById(R.id.tv_upvotes);
            mTitle = itemView.findViewById(R.id.tv_title);
            mAuthor = itemView.findViewById(R.id.tv_author);
            mSubreddit = itemView.findViewById(R.id.tv_subreddit);
            mNumComments = itemView.findViewById(R.id.tv_comments);
            mPostImage = itemView.findViewById(R.id.iv_post);
        }

        @SuppressLint("DefaultLocale")
        void bind(Context context, Post post) {
            mUps.setText(String.format("%d", post.getNumComments()));
            mTitle.setText(post.getTitle());
            mAuthor.setText(String.format("Posted by u/%s", post.getAuthor()));
            mSubreddit.setText(post.getSubredditNamePrefixed());
            mNumComments.setText(String.format("%d", post.getNumComments()));

            ImageUtils.setMedia(context, mPostImage, post);
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
