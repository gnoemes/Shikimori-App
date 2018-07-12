package com.gnoemes.shikimoriapp.entity.series.presentation;

import java.io.Serializable;

public class PlayVideoNavigationData implements Serializable {

    private long animeId;
    private int episodeId;
    private long videoId;
    private int episodes;

    public PlayVideoNavigationData(long animeId, int episodeId, long videoId, int episodes) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.videoId = videoId;
        this.episodes = episodes;
    }

    public long getAnimeId() {
        return animeId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public long getVideoId() {
        return videoId;
    }

    public int getEpisodesSize() {
        return episodes;
    }
}
