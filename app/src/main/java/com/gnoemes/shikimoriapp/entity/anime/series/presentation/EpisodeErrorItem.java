package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;

public class EpisodeErrorItem extends BaseEpisodeItem {

    private String errorMessage;

    public EpisodeErrorItem(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
