package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

public class TranslationNavigationData {

    private long animeId;
    private int episodeId;
    private long rateId;
    private TranslationType type;

    public TranslationNavigationData(long animeId,
                                     int episodeId,
                                     long rateId,
                                     TranslationType type) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.rateId = rateId;
        this.type = type;
    }

    public long getAnimeId() {
        return animeId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public long getRateId() {
        return rateId;
    }

    public TranslationType getType() {
        return type;
    }
}
