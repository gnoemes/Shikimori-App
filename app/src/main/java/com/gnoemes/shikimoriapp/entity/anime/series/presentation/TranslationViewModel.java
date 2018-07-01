package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationQuality;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

public class TranslationViewModel {

    private long id;
    private String title;
    private String resolution;
    private TranslationQuality quality;
    private TranslationType type;
    private String url;

    public TranslationViewModel(long id,
                                String title,
                                String resolution,
                                TranslationQuality quality,
                                TranslationType type,
                                String url) {
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

    public TranslationType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
