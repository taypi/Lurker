package com.taypih.lurker.model;

import com.taypih.lurker.utils.UrlUtils;

public class PostMedia {
    private String url;
    private String mediaUrl;
    private boolean isVideo;

    public PostMedia(String url, String mediaUrl, boolean isVideo) {
        this.url = UrlUtils.getFixedUrl(url);
        this.mediaUrl = UrlUtils.getFixedUrl(mediaUrl);
        this.isVideo = isVideo;
    }

    public String getUrl() {
        return url;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public boolean isVideo() {
        return isVideo;
    }
}
