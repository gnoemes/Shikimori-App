package com.gnoemes.shikimoriapp.entity.series.domain;

public class PlayVideo {

    private long animeId;
    private int episodeId;
    private VideoHosting hosting;
    private String url;

    public PlayVideo(long animeId, int episodeId, VideoHosting hosting, String url) {
        this.animeId = animeId;
        this.episodeId = episodeId;
        this.hosting = hosting;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
}
