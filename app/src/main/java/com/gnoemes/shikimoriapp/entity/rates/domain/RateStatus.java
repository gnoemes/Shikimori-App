package com.gnoemes.shikimoriapp.entity.rates.domain;

import com.google.gson.annotations.SerializedName;

public enum RateStatus {
    @SerializedName("watching")
    WATCHING("watching"),
    @SerializedName("planned")
    PLANNED("planned"),
    @SerializedName("rewatching")
    REWATCHING("rewatching"),
    @SerializedName("completed")
    COMPLETED("completed"),
    @SerializedName("on_hold")
    ON_HOLD("on_hold"),
    @SerializedName("dropped")
    DROPPED("dropped");

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
