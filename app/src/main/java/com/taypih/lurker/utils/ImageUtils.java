package com.taypih.lurker.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taypih.lurker.R;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.model.PostMedia;

public class ImageUtils {

    public static void setImage(ImageView view, Post post) {
        PostMedia media = post.getContent();
        String url = (!media.isVideo() && media.getMediaUrl() != null) ? media.getMediaUrl() : "";
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(view);
    }
}
