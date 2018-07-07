package com.gnoemes.shikimoriapp.entity.series.domain;

import android.support.annotation.Nullable;

import java.util.List;

public class Series {

    List<Episode> episodes;
    @Nullable
    private String errorMessage;
    private int episodesSize;
    private boolean hasError;

    public Series(@Nullable String errorMessage, boolean hasError, List<Episode> episodes, int episodesSize) {
        this.errorMessage = errorMessage;
        this.hasError = hasError;
        this.episodes = episodes;
        this.episodesSize = episodesSize;
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

    public int getEpisodesSize() {
        return episodesSize;
    }
}
