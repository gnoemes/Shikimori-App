package com.gnoemes.shikimoriapp.entity.anime.series.domain;

import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;

public class Translation {

    private long animeId;
    private int episodeId;
    private long videoId;
    private TranslationType type;
    private TranslationQuality quality;
    private VideoHosting hosting;
    private String author;
    private boolean isValid;
    private int episodesSize;

    public Translation(long animeId,
                       int episodeId,
                       long videoId,
                       TranslationType type,
                       TranslationQuality quality,
                       VideoHosting hosting,
                       String author,
                       boolean isValid,
                       int episodesSize) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.videoId = videoId;
        this.type = type;
        this.quality = quality;
        this.hosting = hosting;
        this.author = author;
        this.isValid = isValid;
        this.episodesSize = episodesSize;
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

    public TranslationType getType() {
        return type;
    }

    public TranslationQuality getQuality() {
        return quality;
    }

    public VideoHosting getHosting() {
        return hosting;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isValid() {
        return isValid;
    }

    public int getEpisodesSize() {
        return episodesSize;
    }
}
