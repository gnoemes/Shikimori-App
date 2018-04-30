package com.gnoemes.shikimoriapp.entity.anime.series.domain;

import java.util.List;

/**
 * Domain series model
 */
public class Series {

    private long id;
    private long animeId;
    private int episodesCount;
    private List<Episode> episodes;

    public Series(long id, long animeId, int episodesCount, List<Episode> episodes) {
        this.id = id;
        this.animeId = animeId;
        this.episodesCount = episodesCount;
        this.episodes = episodes;
    }

    public long getId() {
        return id;
    }

    public long getAnimeId() {
        return animeId;
    }

    public int getEpisodesCount() {
        return episodesCount;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
