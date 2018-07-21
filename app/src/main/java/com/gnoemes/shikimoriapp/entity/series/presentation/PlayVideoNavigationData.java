package com.gnoemes.shikimoriapp.entity.series.presentation;

import java.io.Serializable;

public class PlayVideoNavigationData implements Serializable {

    private long animeId;
    private int episodeId;
    private long videoId;
    private int episodes;
    private long rateId;

    public PlayVideoNavigationData(long animeId, int episodeId, long videoId, int episodes, long rateId) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.videoId = videoId;
        this.episodes = episodes;
        this.rateId = rateId;
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

    public long getRateId() {
        return rateId;
    }
}
