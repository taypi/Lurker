package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }
    }

}
