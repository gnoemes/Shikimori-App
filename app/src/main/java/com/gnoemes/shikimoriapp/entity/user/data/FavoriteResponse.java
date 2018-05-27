package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

public class FavoriteResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("russian")
    private String russianName;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("url")
    private String url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }
}
