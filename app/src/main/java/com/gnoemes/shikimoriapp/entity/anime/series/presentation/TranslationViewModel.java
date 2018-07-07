package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationQuality;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;

public class TranslationViewModel {

    private long animeId;
    private int episodeId;
    private long videoId;
    private TranslationType type;
    private TranslationQuality quality;
    private VideoHosting hosting;
    private String author;

    public TranslationViewModel(long animeId,
                                int episodeId,
                                long videoId,
                                TranslationType type,
                                TranslationQuality quality,
                                VideoHosting hosting,
                                String author) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.videoId = videoId;
        this.type = type;
        this.quality = quality;
        this.hosting = hosting;
        this.author = author;
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
}
