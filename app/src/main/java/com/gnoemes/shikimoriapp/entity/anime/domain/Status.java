package com.gnoemes.shikimoriapp.entity.anime.domain;

import com.google.gson.annotations.SerializedName;

public enum Status {
    @SerializedName("anons")
    ANONS("anons"),
    @SerializedName("ongoing")
    ONGOING("ongoing"),
    @SerializedName("released")
    RELEASED("released"),
    NONE("none");


    private final String status;

    Status(String status) {
        this.status = status;
    }

    public boolean equalsStatus(String otherStatus) {
        return status.equals(otherStatus);
    }

    @Override
    public String toString() {
        return status;
    }
}
