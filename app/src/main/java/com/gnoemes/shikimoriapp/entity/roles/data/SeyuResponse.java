package com.gnoemes.shikimoriapp.entity.roles.data;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeImageResponse;
import com.google.gson.annotations.SerializedName;

public class SeyuResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private AnimeImageResponse imageResponse;
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

    public AnimeImageResponse getImageResponse() {
        return imageResponse;
    }

    public String getUrl() {
        return url;
    }
}
