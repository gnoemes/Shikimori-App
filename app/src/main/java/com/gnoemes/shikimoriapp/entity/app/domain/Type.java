package com.gnoemes.shikimoriapp.entity.app.domain;

public enum Type {
    ANIME("Anime"),
    MANGA("Manga"),
    RANOBE("ranobe"),
    CHARACTER("Character"),
    PERSON("Person"),
    USER("User");

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
