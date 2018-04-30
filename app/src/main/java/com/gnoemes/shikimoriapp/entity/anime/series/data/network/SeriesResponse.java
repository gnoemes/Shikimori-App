package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesResponse {

    @SerializedName("episodes")
    private List<EpisodeResponse> episodes;
    @SerializedName("id")
    private long id;
    @SerializedName("myAnimeListId")
    private long animeId;
    @SerializedName("numberOfEpisodes")
    private int episodesCount;
    @SerializedName("year")
    private int year;
    @SerializedName("type")
    private String type;

    public long getId() {
        return id;
    }

    public long getAnimeId() {
        return animeId;
    }

    public int getEpisodesCount() {
        return episodesCount;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public List<EpisodeResponse> getEpisodes() {
        return episodes;
    }
}
