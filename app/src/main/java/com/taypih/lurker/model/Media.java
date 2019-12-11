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
    }

    private class RedditVideo {
        @SerializedName("fallback_url")
        @Expose
        private String fallbackUrl;

        public String getFallbackUrl() {
            return fallbackUrl;
        }
    }
}