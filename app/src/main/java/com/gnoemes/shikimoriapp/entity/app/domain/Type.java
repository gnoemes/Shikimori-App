package com.gnoemes.shikimoriapp.entity.app.domain;

public enum Type {
    ANIME("Anime"),
    MANGA("Manga");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public boolean isEqualType(String otherType) {
        return type.equals(otherType);
    }

    public String getType() {
        return type;
    }
}
