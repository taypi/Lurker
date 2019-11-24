package com.taypih.lurker.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Preview extends Media {

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("reddit_video_preview")
    @Expose
    private RedditVideoPreview redditVideoPreview;

    @Override
    public String getUrl() {
        return redditVideoPreview != null ?
                redditVideoPreview.fallbackUrl : images.get(0).getUrl();
    }

    @Override
    public boolean isGif() {
        return redditVideoPreview != null && redditVideoPreview.getIsGif();
    }

    @Override
    public boolean isVideo() {
        return false;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public RedditVideoPreview getRedditVideoPreview() {
        return redditVideoPreview;
    }

    public void setRedditVideoPreview(RedditVideoPreview redditVideoPreview) {
        this.redditVideoPreview = redditVideoPreview;
    }

    public class RedditVideoPreview {

        @SerializedName("fallback_url")
        @Expose
        private String fallbackUrl;
        @SerializedName("duration")
        @Expose
        private Integer duration;
        @SerializedName("is_gif")
        @Expose
        private Boolean isGif;

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

        public Boolean getIsGif() {
            return isGif;
        }

        public void setIsGif(Boolean isGif) {
            this.isGif = isGif;
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
