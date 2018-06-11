package com.gnoemes.shikimoriapp.entity.manga.domain;

public enum MangaStatus {
    ANONS("anons"),
    ONGOING("ongoing"),
    RELEASED("released");

    private final String status;

    MangaStatus(String status) {
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
