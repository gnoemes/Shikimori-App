package com.gnoemes.shikimoriapp.entity.app.domain;

public enum ClubPolicy {
    FREE("free"),
    ADMIN_INVITE("admin_invite"),
    OWNER_INVITE("owner_invite");

    private final String type;

    ClubPolicy(String type) {
        this.type = type;
    }

    public boolean isEqualType(String otherType) {
        return type.equals(otherType);
    }

    public String getType() {
        return type;
    }
}
