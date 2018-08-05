package com.gnoemes.shikimoriapp.entity.forum.data;

import com.google.gson.annotations.SerializedName;

public class ForumResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("permalink")
    private String forumType;
    @SerializedName("url")
    private String url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForumType() {
        return forumType;
    }

    public String getUrl() {
        return url;
    }
}
