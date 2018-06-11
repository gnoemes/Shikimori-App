package com.gnoemes.shikimoriapp.entity.anime.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class AnimeFranchiseNodeResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("date")
    private long dateTime;
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("url")
    private String url;
    @SerializedName("year")
    @Nullable
    private Integer year;
    @SerializedName("kind")
    private String type;
    @SerializedName("weight")
    private int weight;

    public long getId() {
        return id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public Integer getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }
}
