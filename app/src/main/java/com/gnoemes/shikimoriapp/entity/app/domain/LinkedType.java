package com.gnoemes.shikimoriapp.entity.app.domain;

import com.google.gson.annotations.SerializedName;

public enum LinkedType {
    @SerializedName("Anime")
    ANIME("Anime"),
    @SerializedName("Manga")
    MANGA("Manga"),
    @SerializedName("Ranobe")
    RANOBE("Ranobe"),
    @SerializedName("Collection")
    COLLECTION("Collection"),
    @SerializedName("Character")
    CHARACTER("Character"),
    @SerializedName("Contest")
    CONTEST("Contest"),
    @SerializedName("Club")
    CLUB("Club"),
    @SerializedName("Review")
    REVIEW("Review"),
    @SerializedName("Person")
    PERSON("Person"),
    @SerializedName("CosplayGallery")
    COSPLAY_GALLERY("CosplayGallery"),
    @SerializedName("")
    UNKNOWN("");

    private final String type;

    LinkedType(String type) {
        this.type = type;
    }

    public boolean isEqualType(String otherType) {
        return type.equals(otherType);
    }

    public String getType() {
        return type;
    }
}
