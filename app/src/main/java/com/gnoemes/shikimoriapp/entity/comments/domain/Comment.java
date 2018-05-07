package com.gnoemes.shikimoriapp.entity.comments.domain;

import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import org.joda.time.DateTime;

public class Comment {

    private long id;
    private long userId;
    private long commentableId;
    private String body;
    private DateTime createdDate;
    private boolean isSummary;
    private UserBrief userBrief;

    public Comment(long id, long userId, long commentableId,
                   String body, DateTime createdDate, boolean isSummary,
                   UserBrief userBrief) {
        this.id = id;
        this.userId = userId;
        this.commentableId = commentableId;
        this.body = body;
        this.createdDate = createdDate;
        this.isSummary = isSummary;
        this.userBrief = userBrief;
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

    public String getBody() {
        return body;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public boolean isSummary() {
        return isSummary;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }
}
