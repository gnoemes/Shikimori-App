package com.gnoemes.shikimoriapp.entity.anime.domain;

public enum Status {
    ANONS("anons"),
    ONGOING("ongoing"),
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
