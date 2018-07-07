package com.gnoemes.shikimoriapp.entity.series.domain;

import android.support.annotation.Nullable;

import java.util.List;

public class Series {

    List<Episode> episodes;
    @Nullable
    private String errorMessage;
    private boolean hasError;

    public Series(@Nullable String errorMessage, boolean hasError, List<Episode> episodes) {
        this.errorMessage = errorMessage;
        this.hasError = hasError;
        this.episodes = episodes;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isHasError() {
        return hasError;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
