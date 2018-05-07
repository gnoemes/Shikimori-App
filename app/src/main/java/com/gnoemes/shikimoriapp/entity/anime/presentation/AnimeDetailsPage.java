package com.gnoemes.shikimoriapp.entity.anime.presentation;

public enum AnimeDetailsPage {
    COMMENTS(0),
    INFO(1),
    SERIES(2),;

    private final int page;

    AnimeDetailsPage(int page) {
        this.page = page;
    }

    public boolean isEqualPage(int page) {
        return this.page == page;
    }

    public int getPage() {
        return page;
    }
}
