package com.gnoemes.shikimoriapp.entity.rates.domain;

import android.support.annotation.Nullable;

import java.io.Serializable;

public class UserRate implements Serializable {
    private long id;
    private RateStatus status;
    @Nullable
    private String score;
    @Nullable
    private String text;
    @Nullable
    private String episodes;
    @Nullable
    private String rewatches;

    public UserRate(long id, RateStatus status, @Nullable String score,
                    @Nullable String text, @Nullable String episodes, @Nullable String rewatches) {
        this.id = id;
        this.score = score;
        this.status = status;
        this.text = text;
        this.episodes = episodes;
        this.rewatches = rewatches;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public String getScore() {
        return score;
    }

    public RateStatus getStatus() {
        return status;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Nullable
    public String getEpisodes() {
        return episodes;
    }

    @Nullable
    public String getRewatches() {
        return rewatches;
    }
}
