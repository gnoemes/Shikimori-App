package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

public class AnimeContentItem extends BaseAnimeItem {

    private long id;
    private String description;

    public AnimeContentItem(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
