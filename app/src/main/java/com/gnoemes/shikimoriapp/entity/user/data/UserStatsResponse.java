package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

public class UserStatsResponse {

    @SerializedName("full_statuses")
    private FullStatusResponse statusResponse;

    @SerializedName("has_anime")
    private boolean hasAnime;

    public UserStatsResponse(FullStatusResponse statusResponse, boolean hasAnime) {
        this.statusResponse = statusResponse;
        this.hasAnime = hasAnime;
    }

    public FullStatusResponse getStatusResponse() {
        return statusResponse;
    }

    public boolean isHasAnime() {
        return hasAnime;
    }
}
