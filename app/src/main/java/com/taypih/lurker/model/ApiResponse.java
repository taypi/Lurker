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

    private class Data {

        @SerializedName("children")
        @Expose
        private List<Post> posts = null;

        List<Post> getPosts() {
            return posts;
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }
    }

}
