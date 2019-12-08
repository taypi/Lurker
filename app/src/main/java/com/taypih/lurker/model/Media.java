package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taypih.lurker.utils.UrlUtils;

public class Media {
    @SerializedName("oembed")
    @Expose
    private Oembed oembed;
    @SerializedName("reddit_video")
    @Expose
    private RedditVideo redditVideo;

    public Oembed getOembed() {
        return oembed;
    }

    public void setOembed(Oembed oembed) {
        this.oembed = oembed;
    }

    public RedditVideo getRedditVideo() {
        return redditVideo;
    }

    public void setRedditVideo(RedditVideo redditVideo) {
        this.redditVideo = redditVideo;
    }

    public String getGifUrl() {
        return oembed == null ? null : oembed.getThumbnailUrl();
    }

    public String getVideoFallbackUrl() {
        return redditVideo == null ? null : redditVideo.getFallbackUrl();
    }

    private class Oembed {
        @SerializedName("thumbnail_url")
        @Expose
        private String thumbnailUrl;

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }

    private class RedditVideo {
        @SerializedName("fallback_url")
        @Expose
        private String fallbackUrl;

        public String getFallbackUrl() {
            return fallbackUrl;
        }

        public void setFallbackUrl(String fallbackUrl) {
            this.fallbackUrl = fallbackUrl;
        }
    }
}