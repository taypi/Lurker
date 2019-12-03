package com.taypih.lurker.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getId() {
        return data.id;
    }

    public Media getMedia() {
        return data.getMedia();
    }

    public String getTitle() {
        return data.getTitle();
    }

    public String getSubredditNamePrefixed() {
        return data.subredditNamePrefixed;
    }

    public Integer getUps() {
        return data.ups;
    }

    public Boolean isOver18() {
        return data.over18;
    }

    public String getAuthor() {
        return data.author;
    }

    public Integer getNumComments() {
        return data.numComments;
    }

    public Double getCreatedUtc() {
        return data.createdUtc;
    }

    public Boolean isVideo() {
        return data.isVideo;
    }

    public static DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    private class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("subreddit_name_prefixed")
        @Expose
        private String subredditNamePrefixed;
        @SerializedName("ups")
        @Expose
        private Integer ups;
        @SerializedName("over_18")
        @Expose
        private Boolean over18;
        @SerializedName("preview")
        @Expose
        private Preview preview;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("num_comments")
        @Expose
        private Integer numComments;
        @SerializedName("created_utc")
        @Expose
        private Double createdUtc;
        @SerializedName("video")
        @Expose
        private Video video;
        @SerializedName("is_video")
        @Expose
        private Boolean isVideo;

        String getTitle() {
            return title;
        }

        Media getMedia() {
            return video != null ? video : preview;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubredditNamePrefixed() {
            return subredditNamePrefixed;
        }

        public void setSubredditNamePrefixed(String subredditNamePrefixed) {
            this.subredditNamePrefixed = subredditNamePrefixed;
        }

        public Integer getUps() {
            return ups;
        }

        public void setUps(Integer ups) {
            this.ups = ups;
        }

        public Boolean getOver18() {
            return over18;
        }

        public void setOver18(Boolean over18) {
            this.over18 = over18;
        }

        public Preview getPreview() {
            return preview;
        }

        public void setPreview(Preview preview) {
            this.preview = preview;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Integer getNumComments() {
            return numComments;
        }

        public void setNumComments(Integer numComments) {
            this.numComments = numComments;
        }

        public Double getCreatedUtc() {
            return createdUtc;
        }

        public void setCreatedUtc(Double createdUtc) {
            this.createdUtc = createdUtc;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Boolean getIsVideo() {
            return isVideo;
        }

        public void setIsVideo(Boolean isVideo) {
            this.isVideo = isVideo;
        }
    }
}