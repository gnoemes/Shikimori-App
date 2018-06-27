package com.gnoemes.shikimoriapp.entity.anime.domain;

public enum AnimeStatus {
    ANONS("anons"),
    ONGOING("ongoing"),
    RELEASED("released"),
    NONE("none");


    private final String status;

    AnimeStatus(String status) {
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
