package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public List<Comment> getComments() {
        return data.getComments();
    }

    private class Data {
        @SerializedName("children")
        @Expose
        private List<Comment> comments = null;

        public List<Comment> getComments() {
            return comments;
        }
    }
}
