package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public List<Post> getPosts() {
        return data.getPosts();
    }

    public String getAfter() {
        return data.getAfter();
    }

    public String getBefore() {
        return data.getBefore();
    }

    private class Data {
        @SerializedName("children")
        @Expose
        private List<Post> posts = null;
        @SerializedName("after")
        @Expose
        private String after = null;
        @SerializedName("before")
        @Expose
        private String before = null;

        List<Post> getPosts() {
            return posts;
        }

        public String getAfter() {
            return after;
        }

        public String getBefore() {
            return before;
        }
    }

}
