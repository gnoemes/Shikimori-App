package com.gnoemes.shikimoriapp.entity.anime.domain;

public enum AnimeType {
    TV("tv"),
    MOVIE("movie"),
    OVA("ova"),
    ONA("ona"),
    SPECIAL("special"),
    MUSIC("music");


    private final String type;

    AnimeType(String type) {
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
