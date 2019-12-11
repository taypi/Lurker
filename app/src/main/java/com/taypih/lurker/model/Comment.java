package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("data")
    @Expose
    private CommentData commentData;

    public CommentData getData() {
        return commentData;
    }

    public void setData(CommentData commentData) {
        this.commentData = commentData;
    }

    public String getId() {
        return commentData.getId();
    }

    public String getAuthor() {
        return commentData.getAuthor();
    }

    public String getContent() {
        return commentData.getBody();
    }

    public int getUps() {
        return commentData.getUps();
    }

    private class CommentData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("ups")
        @Expose
        private Integer ups;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("body")
        @Expose
        private String body;

        public String getId() {
            return id;
        }

        public Integer getUps() {
            return ups;
        }

        public String getAuthor() {
            return author;
        }

        public String getBody() {
            return body;
        }
    }
}
