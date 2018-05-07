package com.gnoemes.shikimoriapp.entity.comments.domain;

public enum CommentableType {
    TOPIC("topic"),
    USER_COMMENTS("user");

    private final String type;

    CommentableType(String type) {
        this.type = type;
    }

    public boolean isEqualType(String otherType) {
        return type.equals(otherType);
    }

    public String getType() {
        return type;
    }
}
