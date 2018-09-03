package com.gnoemes.shikimoriapp.entity.anime.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class AnimeResponse extends LinkedContentResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @Nullable
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private ImageResponse image;
    @SerializedName("url")
    private String url;
    @SerializedName("kind")
    private String type;
    @SerializedName("status")
    private String status;
    @SerializedName("episodes")
    private int episodes;
    @SerializedName("episodes_aired")
    private int episodesAired;
    @SerializedName("aired_on")
    private DateTime airedDate;
    @Nullable
    @SerializedName("released_on")
    private DateTime releasedDate;

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

    public ImageResponse getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getEpisodesAired() {
        return episodesAired;
    }

    public DateTime getAiredDate() {
        return airedDate;
    }

    @Nullable
    public DateTime getReleasedDate() {
        return releasedDate;
    }
}
