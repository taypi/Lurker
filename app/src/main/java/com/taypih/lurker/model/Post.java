package com.taypih.lurker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post implements Parcelable {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getId() {
        return data.getId();
    }

    public String getTitle() {
        return data.getTitle();
    }

    public String getSubredditNamePrefixed() {
        return data.getSubredditNamePrefixed();
    }

    public Integer getUps() {
        return data.getUps();
    }

    public Boolean isOver18() {
        return data.getOver18();
    }

    public String getAuthor() {
        return data.getAuthor();
    }

    public Integer getNumComments() {
        return data.getNumComments();
    }

    public Double getCreatedUtc() {
        return data.getCreatedUtc();
    }

    public PostMedia getContent() {
        return data.getPostMedia();
    }

    protected Post(Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(data);
    }

    public final static Parcelable.Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return (new Post[size]);
        }
    };
}