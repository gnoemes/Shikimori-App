package com.gnoemes.shikimoriapp.entity.roles.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse;
import com.google.gson.annotations.SerializedName;

public class CharacterResponse extends LinkedContentResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @Nullable
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private ImageResponse imageResponse;
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

    public ImageResponse getImageResponse() {
        return imageResponse;
    }

    public String getUrl() {
        return url;
    }
}
