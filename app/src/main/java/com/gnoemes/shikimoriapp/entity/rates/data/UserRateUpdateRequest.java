package com.gnoemes.shikimoriapp.entity.rates.data;

import com.google.gson.annotations.SerializedName;

public class UserRateUpdateRequest {

    @SerializedName("user_rate")
    private UserRateUpdateRequest createRequest;

    @SerializedName("rewatches")
    private String rewatches;

    @SerializedName("episodes")
    private String episodes;

    @SerializedName("score")
    private String score;

    @SerializedName("status")
    private String status;

    @SerializedName("text")
    private String text;

    public UserRateUpdateRequest(UserRateUpdateRequest createRequest) {
        this.createRequest = createRequest;
    }

    public UserRateUpdateRequest(String rewatches, String episodes,
                                 String score, String status, String text) {
        this.rewatches = rewatches;
        this.episodes = episodes;
        this.score = score;
        this.status = status;
        this.text = text;
    }
}
