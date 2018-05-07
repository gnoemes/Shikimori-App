package com.gnoemes.shikimoriapp.entity.comments.data;

import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class CommentResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("user_id")
    private long userId;

    /**
     * id of topic or user comment
     */
    @SerializedName("commentable_id")
    private long commentableId;

    /**
     * Topic or user
     */
    @SerializedName("commentable_type")
    private String type;

    @SerializedName("body")
    private String body;

    @SerializedName("created_at")
    private DateTime createdDate;

    @SerializedName("updated_at")
    private DateTime updatedDate;

    @SerializedName("is_offtopic")
    private boolean isOfftopic;

    @SerializedName("is_summary")
    private boolean isSummary;

    @SerializedName("can_be_edited")
    private boolean canBeEdited;

    @SerializedName("user")
    private UserBriefResponse userBriefResponse;

    public UserBriefResponse getUserBriefResponse() {
        return userBriefResponse;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getCommentableId() {
        return commentableId;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public DateTime getUpdatedDate() {
        return updatedDate;
    }

    public boolean isOfftopic() {
        return isOfftopic;
    }

    public boolean isSummary() {
        return isSummary;
    }

    public boolean isCanBeEdited() {
        return canBeEdited;
    }
}
