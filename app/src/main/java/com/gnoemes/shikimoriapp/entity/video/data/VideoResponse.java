package com.gnoemes.shikimoriapp.entity.video.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class VideoResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    @Nullable
    private String name;
    @SerializedName("kind")
    private String kind;
    @SerializedName("hosting")
    private String hosting;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getHosting() {
        return hosting;
    }
}
