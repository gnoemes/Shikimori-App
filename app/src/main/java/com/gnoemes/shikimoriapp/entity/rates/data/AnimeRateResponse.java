package com.gnoemes.shikimoriapp.entity.rates.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
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

    @Nullable
    @SerializedName("chapters")
    private Integer chapters;

    @Nullable
    @SerializedName("volumes")
    private Integer volumes;

    @SerializedName("rewatches")
    private int rewatches;

    @Nullable
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

    @Nullable
    public Integer getChapters() {
        return chapters;
    }

    @Nullable
    public Integer getVolumes() {
        return volumes;
    }

    public int getRewatches() {
        return rewatches;
    }

    @Nullable
    public UserBriefResponse getUserBriefResponse() {
        return userBriefResponse;
    }

    @Nullable
    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }
}
