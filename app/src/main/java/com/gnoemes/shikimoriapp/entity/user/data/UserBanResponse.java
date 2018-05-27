package com.gnoemes.shikimoriapp.entity.user.data;

import com.gnoemes.shikimoriapp.entity.comments.data.CommentResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class UserBanResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("user_id")
    private long userId;
    @SerializedName("comment")
    private CommentResponse comment;
    @SerializedName("moderator_id")
    private long moderatorId;
    @SerializedName("reason")
    private String reason;
    @SerializedName("created_at")
    private DateTime createdAt;
    @SerializedName("duration_minutes")
    private int duration;
    @SerializedName("user")
    private UserBriefResponse userBriefResponse;
    @SerializedName("moderator")
    private UserBriefResponse moderatorBriefResponse;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public CommentResponse getComment() {
        return comment;
    }

    public long getModeratorId() {
        return moderatorId;
    }

    public String getReason() {
        return reason;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public int getDuration() {
        return duration;
    }

    public UserBriefResponse getUserBriefResponse() {
        return userBriefResponse;
    }

    public UserBriefResponse getModeratorBriefResponse() {
        return moderatorBriefResponse;
    }
}
