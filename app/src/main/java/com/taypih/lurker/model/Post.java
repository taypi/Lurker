package com.taypih.lurker.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taypih.lurker.utils.UrlUtils;

import retrofit2.http.Url;

public class Post {

    @SerializedName("data")
    @Expose
    private Data data;

    @Override
    public boolean equals(@Nullable Object obj) {
        Post post = (Post) obj;
        return post != null && data.getTitle().equals(post.getTitle());
    }

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
        return data.isVideo();
    }

    public PostMedia getPostMedia() {
        return data.getPostMedia();
    }

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
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("media")
        @Expose
        private Media media;
        @SerializedName("is_video")
        @Expose
        private Boolean isVideo;

        String getTitle() {
            return title;
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

        public PostMedia getPostMedia() {
            if (UrlUtils.isGif(url)) {
                return new PostMedia(null, url, false);
            } else if (media != null && UrlUtils.isGif(media.getGifUrl())) {
                return new PostMedia(null, media.getGifUrl(), false);
            } else if (UrlUtils.isRedditVideo(url)) {
                return new PostMedia(null, url, true);
            } else if (getIsVideo() && media != null && media.getVideoFallbackUrl() != null) {
                return new PostMedia(null, media.getGifUrl(), true);
            } else if (preview != null && preview.getVideoUrl() != null) {
                return new PostMedia(null, preview.getVideoUrl(), true);
            } else if (UrlUtils.isImage(url)) {
                return new PostMedia(null, url, false);
            } else if (preview != null && preview.getImageUrl() != null) {
                return new PostMedia(url, preview.getImageUrl(), false);
            } else {
                return new PostMedia(url, null, false);
            }
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Media getMedia() {
            return media;
        }

        public Boolean getIsVideo() {
            return isVideo;
        }

        public void setIsVideo(Boolean isVideo) {
            this.isVideo = isVideo;
        }

        public boolean isGif() {
            return UrlUtils.isGif(url) || UrlUtils.isGif(media.getGifUrl());
        }

        public String getPreviewFallbackUrl() {
            return preview.getVideoUrl();
        }

        public Boolean isVideo() {
            return getIsVideo()  && media.getVideoFallbackUrl() != null;
        }
    }
}