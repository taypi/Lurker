package com.taypih.lurker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taypih.lurker.utils.UrlUtils;

public class Data implements Parcelable {

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelfText() {
        return selfText;
    }

    public void setSelfText(String selfText) {
        this.selfText = selfText;
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

    protected Data(Parcel in) {
            title = in.readString();
            selfText = in.readString();
            subredditNamePrefixed = in.readString();
            ups = in.readInt();
            author = in.readString();
            numComments = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(title);
        dest.writeString(selfText);
        dest.writeString(subredditNamePrefixed);
        dest.writeInt(ups);
        dest.writeString(author);
        dest.writeInt(numComments);
    }

    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };
}
