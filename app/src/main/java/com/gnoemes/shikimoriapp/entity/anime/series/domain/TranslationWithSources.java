package com.gnoemes.shikimoriapp.entity.anime.series.domain;

import java.util.List;

public class TranslationWithSources {

    private Translation translation;
    private List<PlayEpisode> sources;

    public TranslationWithSources(Translation translation, List<PlayEpisode> sources) {
        this.translation = translation;
        this.sources = sources;
    }

    public Translation getTranslation() {
        return translation;
    }

    public List<PlayEpisode> getSources() {
        return sources;
    }
}
