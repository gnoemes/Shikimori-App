package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EpisodeResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("episodeFull")
    private String episodeFull;

    @SerializedName("episodeInt")
    private float episode;

    @SerializedName("episodeType")
    private String type;

    @Nullable
    @SerializedName("episodeTitle")
    private String title;

    @SerializedName("firstUploadedDateTime")
    private String firstDate;

    @SerializedName("seriesId")
    private long seriesId;

    @SerializedName("countViews")
    private long views;

    public long getId() {
        return id;
    }

    public String getEpisodeFull() {
        return episodeFull;
    }

    public float getEpisode() {
        return episode;
    }

    public String getType() {
        return type;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public long getViews() {
        return views;
    }
}
