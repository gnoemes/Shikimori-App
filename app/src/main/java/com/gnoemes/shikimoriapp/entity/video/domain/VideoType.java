package com.gnoemes.shikimoriapp.entity.video.domain;

public enum VideoType {
    OPENING("op"),
    ENDING("ed"),
    PROMO("pv"),
    OTHER("other");

    private final String type;

    VideoType(String type) {
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
