package com.gnoemes.shikimoriapp.entity.app.domain;

import com.google.gson.annotations.SerializedName;

public enum Type {
    @SerializedName("Anime")
    ANIME("Anime"),
    @SerializedName("Manga")
    MANGA("Manga"),
    @SerializedName("Ranobe")
    RANOBE("Ranobe"),
    @SerializedName("Character")
    CHARACTER("Character"),
    @SerializedName("Person")
    PERSON("Person"),
    @SerializedName("User")
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
