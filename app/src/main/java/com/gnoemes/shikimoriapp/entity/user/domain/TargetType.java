package com.gnoemes.shikimoriapp.entity.user.domain;

public enum TargetType {
    TV("tv"),
    MOVIE("movie"),
    OVA("ova"),
    ONA("ona"),
    SPECIAL("special"),
    MUSIC("music"),
    MANGA("manga");


    private final String type;

    TargetType(String type) {
        this.type = type;
    }

    public boolean equalsType(String otherType) {
        return type.equals(otherType);
    }

    @Override
    public String toString() {
        return this.type;
    }
}
