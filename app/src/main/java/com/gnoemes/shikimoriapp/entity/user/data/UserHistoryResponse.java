package com.gnoemes.shikimoriapp.entity.user.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class UserHistoryResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("created_at")
    private DateTime actionDate;

    @SerializedName("description")
    private String description;

    @Nullable
    @SerializedName("target")
    private AnimeResponse animeResponse; //TODO not the fact that this anime


    public long getId() {
        return id;
    }

    public DateTime getActionDate() {
        return actionDate;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }
}
