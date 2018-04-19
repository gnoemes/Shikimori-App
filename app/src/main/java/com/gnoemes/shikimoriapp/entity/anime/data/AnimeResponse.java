package com.gnoemes.shikimoriapp.entity.anime.data;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import javax.annotation.Nullable;

public class AnimeResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @Nullable
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private AnimeImageResponse image;
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

    public AnimeImageResponse getImage() {
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
