package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

public class TranslationNavigationData {

    private long episodeId;
    private TranslationType type;

    public TranslationNavigationData(long episodeId, TranslationType type) {
        this.episodeId = episodeId;
        this.type = type;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public TranslationType getType() {
        return type;
    }
}
