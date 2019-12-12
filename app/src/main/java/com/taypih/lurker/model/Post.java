package com.taypih.lurker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Posts")
public class Post implements Parcelable {

    @SerializedName("data")
    @Expose
    @Ignore
    private Data data;
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String subredditName;
    private String author;
    private String url;
    private String mediaUrl;
    private String bodyText;
    private Integer ups;
    private Integer numComments;
    private Boolean hasImage;
    private Boolean hasVideo;

    public Post(@NonNull String id, String title, String subredditName, String author, String url,
                String mediaUrl, String bodyText, int ups, int numComments, boolean hasImage, boolean hasVideo) {
        this.id = id;
        this.title = title;
        this.subredditName = subredditName;
        this.author = author;
        this.url = url;
        this.mediaUrl = mediaUrl;
        this.bodyText = bodyText;
        this.ups = ups;
        this.numComments = numComments;
        this.hasImage = hasImage;
        this.hasVideo = hasVideo;
    }

    public String getId() {
        if (id == null) {
            id = data.getId();
        }
        return id;
    }

    public String getTitle() {
        if (title == null) {
            title = data.getTitle();
        }
        return title;
    }

    public String getSubredditName() {
        if (subredditName == null) {
            subredditName = data.getSubredditNamePrefixed();
        }
        return subredditName;
    }

    public Integer getUps() {
        if (ups == null) {
            ups = data.getUps();
        }
        return ups;
    }

    public String getAuthor() {
        if (author == null) {
            author = data.getAuthor();
        }
        return author;
    }

    public String getUrl() {
        if (url == null) {
            url = data.getPostMedia().getUrl();
        }
        return url;
    }

    public String getMediaUrl() {
        if (mediaUrl == null) {
            mediaUrl = data.getPostMedia().getMediaUrl();
        }
        return mediaUrl;
    }

    public String getBodyText() {
        if (bodyText == null) {
            bodyText = data.getSelfText();
        }
        return bodyText;
    }

    public Integer getNumComments() {
        if (numComments == null) {
            numComments = data.getNumComments();
        }
        return numComments;
    }

    public Boolean hasImage() {
        if (hasImage == null) {
            hasImage = !data.getPostMedia().isVideo() && data.getPostMedia().getMediaUrl() != null;
        }
        return hasImage;
    }

    public Boolean hasVideo() {
        if (hasVideo == null) {
            hasVideo = data.getPostMedia().isVideo();
        }
        return hasVideo;
    }

    protected Post(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.subredditName = in.readString();
        this.author = in.readString();
        this.url = in.readString();
        this.mediaUrl = in.readString();
        this.bodyText = in.readString();
        this.ups = in.readInt();
        this.numComments = in.readInt();
        this.hasImage = in.readInt() == 1;
        this.hasVideo = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subredditName);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(mediaUrl);
        dest.writeString(bodyText);
        dest.writeInt(ups);
        dest.writeInt(numComments);
        dest.writeInt(hasImage ? 1 : 0);
        dest.writeInt(hasVideo ? 1 : 0);
    }

    public final static Parcelable.Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return (new Post[size]);
        }
    };

    @BindingAdapter("glide_src")
    public static void setGlideSrc(ImageView view, String mediaUrl) {
        String url = mediaUrl != null ? mediaUrl : "";
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }

    @BindingAdapter("html_text")
    public static void setHtmlText(TextView view, String text) {
        if (text == null) return;
        String htmlText = text.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("(\r\n|\n)", "<br />");
        view.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}