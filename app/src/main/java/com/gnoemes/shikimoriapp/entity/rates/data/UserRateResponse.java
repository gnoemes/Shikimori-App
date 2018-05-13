package com.gnoemes.shikimoriapp.entity.rates.data;

import com.google.gson.annotations.SerializedName;

public class UserRateResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("score")
    private String score;
    @SerializedName("status")
    private String status;
    @SerializedName("text")
    private String text;
    @SerializedName("episodes")
    private String episodes;
    @SerializedName("rewatches")
    private String rewatches;

    public long getId() {
        return id;
    }

    public String getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getRewatches() {
        return rewatches;
    }
}
