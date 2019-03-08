package com.gnoemes.shikimoriapp.entity.anime.data;

import com.google.gson.annotations.SerializedName;

public class LinkResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("kind")
    private String name;

    @SerializedName("url")
    private String url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
