package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taypih.lurker.utils.UrlUtils;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("selftext")
    @Expose
    private String selfText;
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

    public String getSelfText() {
        return selfText;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public Integer getUps() {
        return ups;
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

    public Integer getNumComments() {
        return numComments;
    }

    public Double getCreatedUtc() {
        return createdUtc;
    }

    public PostMedia getPostMedia() {
        if (UrlUtils.isGif(url)) {
            return new PostMedia(null, url, false);
        } else if (media != null && UrlUtils.isGif(media.getGifUrl())) {
            return new PostMedia(null, media.getGifUrl(), false);
        } else if (media != null && media.getVideoFallbackUrl() != null) {
            return new PostMedia(null, media.getVideoFallbackUrl(), true);
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
}
