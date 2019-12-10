package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubredditResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Subreddit> getSubreddits() {
        return data.getSubreddits();
    }

    private class Data {
        @SerializedName("children")
        @Expose
        private List<Subreddit> subreddits = null;

        public List<Subreddit> getSubreddits() {
            return subreddits;
        }

        public void setChildren(List<Subreddit> subreddits) {
            this.subreddits = subreddits;
        }

    }
}
