package com.gnoemes.shikimoriapp.entity.user.domain;

import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;

import org.joda.time.DateTime;

public class UserBan {

    private long id;
    private long userId;
    private long moderatorId;
    private Comment comment;
    private String reason;
    private DateTime createdAt;
    private int duration;
    private UserBrief user;
    private UserBrief moderator;

    public UserBan(long id, long userId, long moderatorId,
                   Comment comment, String reason, DateTime createdAt,
                   int duration, UserBrief user, UserBrief moderator) {
        this.id = id;
        this.userId = userId;
        this.moderatorId = moderatorId;
        this.comment = comment;
        this.reason = reason;
        this.createdAt = createdAt;
        this.duration = duration;
        this.user = user;
        this.moderator = moderator;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getModeratorId() {
        return moderatorId;
    }

    public Comment getComment() {
        return comment;
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

    public UserBrief getUser() {
        return user;
    }

    public UserBrief getModerator() {
        return moderator;
    }
}
