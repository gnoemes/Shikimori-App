package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

import java.util.List;

public class EpisodeItem extends BaseEpisodeItem {

    private int id;
    private long animeId;
    private String hostings;
    private List<TranslationType> translationTypes;
    private boolean isWatched;

    public EpisodeItem(int id,
                       long animeId,
                       String hostings,
                       List<TranslationType> translationTypes,
                       boolean isWatched) {
        this.id = id;
        this.animeId = animeId;
        this.hostings = hostings;
        this.translationTypes = translationTypes;
        this.isWatched = isWatched;
    }

    public int getId() {
        return id;
    }

    public long getAnimeId() {
        return animeId;
    }

    public String getHostings() {
        return hostings;
    }

    public List<TranslationType> getTranslationTypes() {
        return translationTypes;
    }

    public boolean isWatched() {
        return isWatched;
    }
}
