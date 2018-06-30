package com.gnoemes.shikimoriapp.entity.screenshots.domain;

import java.io.Serializable;

public class ScreenshotNavigationData implements Serializable {
    private long animeId;
    private String name;
    private String posterUrl;

    public ScreenshotNavigationData(long animeId, String name, String posterUrl) {
        this.animeId = animeId;
        this.name = name;
        this.posterUrl = posterUrl;
    }

    public long getAnimeId() {
        return animeId;
    }

    public String getName() {
        return name;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
