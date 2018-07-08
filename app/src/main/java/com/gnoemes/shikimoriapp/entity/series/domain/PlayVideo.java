package com.gnoemes.shikimoriapp.entity.series.domain;

import android.support.annotation.Nullable;

public class PlayVideo {

    private long animeId;
    private int episodeId;
    private VideoHosting hosting;
    private String title;
    @Nullable
    private String url;
    private boolean hasExtra;
    @Nullable
    private VideoExtra extra;

    public PlayVideo(long animeId, int episodeId, VideoHosting hosting, String title, @Nullable String url, boolean hasExtra, @Nullable VideoExtra extra) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.hosting = hosting;
        this.title = title;
        this.url = url;
        this.hasExtra = hasExtra;
        this.extra = extra;
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
    public String getUrl() {
        return url;
    }

    public boolean isHasExtra() {
        return hasExtra;
    }

    @Nullable
    public VideoExtra getExtra() {
        return extra;
    }
}
