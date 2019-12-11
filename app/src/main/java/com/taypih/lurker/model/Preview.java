package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Preview {

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("reddit_video_preview")
    @Expose
    private VideoPreview videoPreview;

    public String getImageUrl() {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0).getUrl();
    }

    public String getVideoUrl() {
        return videoPreview == null ?
                null : videoPreview.getFallbackUrl();
    }

    public class VideoPreview {
        @SerializedName("fallback_url")
        @Expose
        private String fallbackUrl;
        @SerializedName("duration")
        @Expose
        private Integer duration;

        public String getFallbackUrl() {
            return fallbackUrl;
        }
    }

    public class Image {
        @SerializedName("source")
        @Expose
        private Source source;
        @SerializedName("id")
        @Expose
        private String id;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return source.getUrl();
        }

    }

    public class Source {
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("height")
        @Expose
        private Integer height;

        public String getUrl() {
            return url;
        }
    }
}
