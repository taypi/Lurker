package com.taypih.lurker.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taypih.lurker.db.SubredditDatabase;

@Entity(tableName = SubredditDatabase.DATABASE_NAME)
public class Subreddit {
    @SerializedName("data")
    @Expose
    @Ignore
    private Data data;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String iconImg;
    private String displayNamePrefixed;

    public Subreddit(long id, String title, String iconImg, String displayNamePrefixed) {
        this.id = id;
        this.title = title;
        this.iconImg = iconImg;
        this.displayNamePrefixed = displayNamePrefixed;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        this.title = data.getTitle();
        this.iconImg = data.getIconImg();
        this.displayNamePrefixed = data.getDisplayNamePrefixed();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIconImg() {
        return iconImg;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public String getHeaderImg() {
        return data.getHeaderImg();
    }

    @BindingAdapter("roundImage")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    private class Data {
        @SerializedName("header_img")
        @Expose
        private String headerImg;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("icon_img")
        @Expose
        private String iconImg;
        @SerializedName("display_name_prefixed")
        @Expose
        private String displayNamePrefixed;
        @SerializedName("id")
        @Expose
        private String id;

        public String getHeaderImg() {
            return headerImg;
        }

        public void setHeaderImg(String headerImg) {
            this.headerImg = headerImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIconImg() {
            return iconImg;
        }

        public void setIconImg(String iconImg) {
            this.iconImg = iconImg;
        }

        public String getDisplayNamePrefixed() {
            return displayNamePrefixed;
        }

        public void setDisplayNamePrefixed(String displayNamePrefixed) {
            this.displayNamePrefixed = displayNamePrefixed;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
