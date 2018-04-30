package com.gnoemes.shikimoriapp.entity.anime.series.domain;

public enum TranslationQuality {
    BD("bd"),
    TV("tv"),
    DVD("dvd"),;


    private final String quality;

    TranslationQuality(String quality) {
        this.quality = quality;
    }

    public boolean equalQuality(String otherQuality) {
        return quality.equals(otherQuality);
    }

    public String getQuality() {
        return quality;
    }
}
