package com.taypih.lurker.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taypih.lurker.R;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.utils.ImageUtils;

public class DetailsFragment extends Fragment {
    private TextView ups;
    private TextView title;
    private TextView author;
    private TextView subreddit;
    private TextView numComments;
    private ImageView postImage;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ups = view.findViewById(R.id.tv_upvotes);
        title = view.findViewById(R.id.tv_title);
        author = view.findViewById(R.id.tv_author);
        subreddit = view.findViewById(R.id.tv_subreddit);
        numComments = view.findViewById(R.id.tv_comments);
        postImage = view.findViewById(R.id.iv_post);

        Post post = getArguments().getParcelable(Post.class.getSimpleName());

        ups.setText(String.format("%d", post.getNumComments()));
        title.setText(post.getTitle());
        author.setText(String.format("Posted by u/%s", post.getAuthor()));
        subreddit.setText(post.getSubredditNamePrefixed());
        numComments.setText(String.format("%d", post.getNumComments()));

        ImageUtils.setImage(getContext(), postImage, post);
    }
}
