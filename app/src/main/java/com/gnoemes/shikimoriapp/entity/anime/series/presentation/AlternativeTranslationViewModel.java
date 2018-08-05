package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationQuality;

public class AlternativeTranslationViewModel {
    private long id;
    private String title;
    private String resolution;
    private TranslationQuality quality;
    private AlternativeTranslationType type;
    private String url;

    public AlternativeTranslationViewModel(long id, String title, String resolution, TranslationQuality quality, AlternativeTranslationType type, String url) {
        this.id = id;
        this.title = title;
        this.resolution = resolution;
        this.quality = quality;
        this.type = type;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getResolution() {
        return resolution;
    }

    public TranslationQuality getQuality() {
        return quality;
    }

    public AlternativeTranslationType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
