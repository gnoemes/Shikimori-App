package com.gnoemes.shikimoriapp.entity.user.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.google.gson.annotations.SerializedName;

public class AnimeRateResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("score")
    private int score;

    @SerializedName("status")
    private String status;

    @Nullable
    @SerializedName("text")
    private String text;

    @SerializedName("episodes")
    private int episodes;

    @SerializedName("chapters")
    private int chapters;

    @SerializedName("volumes")
    private int volumes;

    @SerializedName("rewatches")
    private int rewatches;

    @SerializedName("user")
    private UserBriefResponse userBriefResponse;

    @Nullable
    @SerializedName("anime")
    private AnimeResponse animeResponse;

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getChapters() {
        return chapters;
    }

    public int getVolumes() {
        return volumes;
    }

    public int getRewatches() {
        return rewatches;
    }

    public UserBriefResponse getUserBriefResponse() {
        return userBriefResponse;
    }

    @Nullable
    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }
}
