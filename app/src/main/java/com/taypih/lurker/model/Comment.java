package com.taypih.lurker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
//        @SerializedName("replies")
//        @Expose
//        private Replies replies;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("body")
        @Expose
        private String body;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getUps() {
            return ups;
        }

        public void setUps(Integer ups) {
            this.ups = ups;
        }

//        public Replies getReplies() {
//            return replies;
//        }
//
//        public void setReplies(Replies replies) {
//            this.replies = replies;
//        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    private class Replies {
        @SerializedName("kind")
        @Expose
        private String kind;
        @SerializedName("data")
        @Expose
        private RepliesData data;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public RepliesData getData() {
            return data;
        }

        public void setData(RepliesData data) {
            this.data = data;
        }

        public List<Comment> getComments() {
            return data.getComments();
        }

    }

    public class RepliesData {
        @SerializedName("children")
        @Expose
        private List<Comment> comments = null;

        public List<Comment> getComments() {
            return comments;
        }

        public void setChildren(List<Comment> comments) {
            this.comments = comments;
        }
    }
}
