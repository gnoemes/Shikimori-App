package com.gnoemes.shikimoriapp.entity.club.data;

import com.google.gson.annotations.SerializedName;

public class ClubResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private ClubImageResponse clubImageResponse;
    @SerializedName("is_censored")
    private boolean isCensored;
    @SerializedName("join_policy")
    private String joinPolicy;
    @SerializedName("comment_policy")
    private String commentPolicy;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ClubImageResponse getClubImageResponse() {
        return clubImageResponse;
    }

    public boolean isCensored() {
        return isCensored;
    }

    public String getJoinPolicy() {
        return joinPolicy;
    }

    public String getCommentPolicy() {
        return commentPolicy;
    }
}
