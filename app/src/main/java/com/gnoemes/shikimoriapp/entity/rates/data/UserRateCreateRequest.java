package com.gnoemes.shikimoriapp.entity.rates.data;

import com.google.gson.annotations.SerializedName;

public class UserRateCreateRequest {

    @SerializedName("user_rate")
    private UserRateCreateRequest createRequest;

    @SerializedName("rewatches")
    private String rewatches;

    @SerializedName("episodes")
    private String episodes;

    @SerializedName("score")
    private String score;

    @SerializedName("status")
    private String status;

    @SerializedName("target_id")
    private String targetId;

    @SerializedName("target_type")
    private String type;

    @SerializedName("text")
    private String text;

    @SerializedName("user_id")
    private String userId;

    public UserRateCreateRequest(UserRateCreateRequest createRequest) {
        this.createRequest = createRequest;
    }

    public UserRateCreateRequest(String rewatches, String episodes, String score,
                                 String status, String targetId, String type,
                                 String text, String userId) {
        this.rewatches = rewatches;
        this.episodes = episodes;
        this.score = score;
        this.status = status;
        this.targetId = targetId;
        this.type = type;
        this.text = text;
        this.userId = userId;
    }
}
