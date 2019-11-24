package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video extends Media {

    @SerializedName("reddit_video")
    @Expose
    private RedditVideo redditVideo;

    @Override
    public String getUrl() {
        return redditVideo.getFallbackUrl();
    }

    @Override
    public boolean isGif() {
        return redditVideo.getIsGif();
    }

    @Override
    public boolean isVideo() {
        return !redditVideo.getIsGif();
    }

    public RedditVideo getRedditVideo() {
        return redditVideo;
    }

    public void setRedditVideo(RedditVideo redditVideo) {
        this.redditVideo = redditVideo;
    }

    public class RedditVideo {

        @SerializedName("fallback_url")
        @Expose
        private String fallbackUrl;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("is_gif")
        @Expose
        private Boolean isGif;

        public String getFallbackUrl() {
            return fallbackUrl;
        }

        public void setFallbackUrl(String fallbackUrl) {
            this.fallbackUrl = fallbackUrl;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Boolean getIsGif() {
            return isGif;
        }

        public void setIsGif(Boolean isGif) {
            this.isGif = isGif;
        }

    }

}
