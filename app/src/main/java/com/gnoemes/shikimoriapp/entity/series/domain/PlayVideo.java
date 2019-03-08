package com.gnoemes.shikimoriapp.entity.series.domain;

import android.support.annotation.Nullable;

import java.util.List;

public class PlayVideo {

    private long animeId;
    private int episodeId;
    private VideoHosting hosting;
    private String title;
    @Nullable
    private String sourceUrl;
    @Nullable
    private List<VideoTrack> tracks;

    public PlayVideo(long animeId, int episodeId, VideoHosting hosting, String title, @Nullable List<VideoTrack> tracks) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.hosting = hosting;
        this.title = title;
        this.tracks = tracks;
    }

    public PlayVideo(long animeId, int episodeId, VideoHosting hosting, String title, @Nullable String sourceUrl) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.hosting = hosting;
        this.title = title;
        this.sourceUrl = sourceUrl;
    }

    public PlayVideo(long animeId, int episodeId, VideoHosting hosting, String title, @Nullable List<VideoTrack> tracks, @Nullable String sourceUrl) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.hosting = hosting;
        this.title = title;
        this.tracks = tracks;
        this.sourceUrl = sourceUrl;
    }

    public long getAnimeId() {
        return animeId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public VideoHosting getHosting() {
        return hosting;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public String getSourceUrl() {
        return sourceUrl;
    }

    @Nullable
    public List<VideoTrack> getTracks() {
        return tracks;
    }
}
