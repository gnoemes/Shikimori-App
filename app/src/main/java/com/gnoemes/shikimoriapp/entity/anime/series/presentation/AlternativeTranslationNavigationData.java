package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;

import java.io.Serializable;

public class AlternativeTranslationNavigationData implements Serializable {

    private long animeId;
    private long episodeId;
    private long rateId;
    private AlternativeTranslationType type;

    public AlternativeTranslationNavigationData(long animeId, long episodeId, long rateId, AlternativeTranslationType type) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.rateId = rateId;
        this.type = type;
    }

    public long getAnimeId() {
        return animeId;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public long getRateId() {
        return rateId;
    }

    public AlternativeTranslationType getType() {
        return type;
    }
}
