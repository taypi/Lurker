package com.taypih.lurker.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taypih.lurker.R;
import com.taypih.lurker.model.Post;
import com.taypih.lurker.model.PostMedia;

public class ImageUtils {

    public static void setImage(Context context, ImageView view, Post post) {
        PostMedia media = post.getPostMedia();
        if (media.isVideo()) {
            Glide.with(context)
                    .load("")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(view);
            Log.d("setMedia", post.getTitle() + " isVideo");
        } else if (media.getMediaUrl() != null) {
            Log.d("setMedia", post.getTitle() + " " + media.getMediaUrl());
            Glide.with(context)
                    .load(media.getMediaUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(view);
        } else if (media.getUrl() != null){
            Log.d("setMedia", post.getTitle() + " has url");
            Glide.with(context)
                    .load("")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(view);
        } else {
            Log.d("setMedia", post.getTitle() + " has no content");
            Glide.with(context)
                    .load("")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(view);
        }
    }
}
