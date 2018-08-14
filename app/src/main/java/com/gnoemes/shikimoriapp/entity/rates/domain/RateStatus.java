package com.gnoemes.shikimoriapp.entity.rates.domain;

public enum RateStatus {
    WATCHING("watching"),
    PLANNED("planned"),
    REWATCHING("rewatching"),
    COMPLETED("completed"),
    ON_HOLD("on_hold"),
    DROPPED("dropped"),
    FAVORITE("fav");

    private final String status;

    RateStatus(String status) {
        this.status = status;
    }

    public boolean isEqualStatus(String otherStatus) {
        return status.equals(otherStatus);
    }

    public String getStatus() {
        return status;
    }
}
