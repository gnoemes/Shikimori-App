package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

public class TranslationViewModel {

    private String authors;
    private String quality;

    public TranslationViewModel(String authors, String quality) {
        this.authors = authors;
        this.quality = quality;
    }

    public String getAuthors() {
        return authors;
    }

    public String getQuality() {
        return quality;
    }
}
