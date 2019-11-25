package com.taypih.lurker.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taypih.lurker.R;
import com.taypih.lurker.model.Media;

public class ImageUtils {

    public static void setImage(Context context, ImageView view, Media media) {
        if (media != null && !media.isVideo()) {
            Glide.with(context)
                    .load(media.getUrl().replace("amp;", ""))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(view);
        }
    }
}
