package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayEpisodeResponse {

    @SerializedName("height")
    private int resolution;
    @SerializedName("urls")
    private List<String> urls;

    public int getResolution() {
        return resolution;
    }

    public List<String> getUrls() {
        return urls;
    }
}
