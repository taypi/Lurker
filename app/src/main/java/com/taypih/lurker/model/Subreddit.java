package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subreddit {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getHeaderImg() {
        return data.getHeaderImg();
    }

    public String getTitle() {
        return data.getTitle();
    }

    public String getIconImg() {
        return data.getIconImg();
    }

    public String getDisplayNamePrefixed() {
        return data.getDisplayNamePrefixed();
    }

    public String getId() {
        return data.getId();
    }

    private class Data {
        @SerializedName("header_img")
        @Expose
        private String headerImg;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("icon_img")
        @Expose
        private String iconImg;
        @SerializedName("display_name_prefixed")
        @Expose
        private String displayNamePrefixed;
        @SerializedName("id")
        @Expose
        private String id;

        public String getHeaderImg() {
            return headerImg;
        }

        public void setHeaderImg(String headerImg) {
            this.headerImg = headerImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIconImg() {
            return iconImg;
        }

        public void setIconImg(String iconImg) {
            this.iconImg = iconImg;
        }

        public String getDisplayNamePrefixed() {
            return displayNamePrefixed;
        }

        public void setDisplayNamePrefixed(String displayNamePrefixed) {
            this.displayNamePrefixed = displayNamePrefixed;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
