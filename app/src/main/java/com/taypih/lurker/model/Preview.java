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

    public VideoPreview getVideoPreview() {
        return videoPreview;
    }

    public void setVideoPreview(VideoPreview videoPreview) {
        this.videoPreview = videoPreview;
    }

    public String getImageUrl() {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0).getUrl();
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

        public void setFallbackUrl(String fallbackUrl) {
            this.fallbackUrl = fallbackUrl;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }

    public class Image {
        @SerializedName("source")
        @Expose
        private Source source;
        @SerializedName("id")
        @Expose
        private String id;

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }
}
