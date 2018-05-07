package com.gnoemes.shikimoriapp.entity.comments.presentation;

import java.util.List;

public class CommentViewModel extends BaseCommentItem {

    private long id;
    private long userId;
    private long commentableId;
    private List<String> body;
    private String createdDate;
    private boolean isSummary;
    private String avatarUrl;
    private String userName;

    public CommentViewModel(long id, long userId, long commentableId,
                            List<String> body, String createdDate, boolean isSummary,
                            String avatarUrl, String userName) {
        this.id = id;
        this.userId = userId;
        this.commentableId = commentableId;
        this.body = body;
        this.createdDate = createdDate;
        this.isSummary = isSummary;
        this.avatarUrl = avatarUrl;
        this.userName = userName;
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

    public List<String> getBody() {
        return body;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public boolean isSummary() {
        return isSummary;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUserName() {
        return userName;
    }
}
