package com.gnoemes.shikimoriapp.entity.roles.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.DefaultImageResponse;
import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.google.gson.annotations.SerializedName;

public class PersonResponse extends LinkedContentResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @Nullable
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private DefaultImageResponse imageResponse;
    @SerializedName("url")
    private String url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getRussianName() {
        return russianName;
    }

    public DefaultImageResponse getImageResponse() {
        return imageResponse;
    }

    public String getUrl() {
        return url;
    }
}
