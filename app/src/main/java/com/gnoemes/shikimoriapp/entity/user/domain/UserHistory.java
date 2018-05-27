package com.gnoemes.shikimoriapp.entity.user.domain;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

public class UserHistory {

    private long id;
    private DateTime actionDate;
    private String description;
    @Nullable
    private Target anime;

    public UserHistory(long id, DateTime actionDate, String description, @Nullable Target anime) {
        this.id = id;
        this.actionDate = actionDate;
        this.description = description;
        this.anime = anime;
    }

    public long getId() {
        return id;
    }

    public DateTime getActionDate() {
        return actionDate;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public Target getTarget() {
        return anime;
    }
}
