package com.gnoemes.shikimoriapp.entity.anime.series.domain;

import android.support.annotation.Nullable;

import java.util.List;

public class PlayEpisode {

    private int resolution;
    private boolean hasSubtitles;
    private List<String> videoUrls;
    @Nullable
    private String subtitles;

    public PlayEpisode(int resolution, boolean hasSubtitles, List<String> videoUrls, @Nullable String subtitles) {
        this.resolution = resolution;
        this.hasSubtitles = hasSubtitles;
        this.videoUrls = videoUrls;
        this.subtitles = subtitles;
    }

    public int getResolution() {
        return resolution;
    }

    public boolean HasSubtitles() {
        return hasSubtitles;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    @Nullable
    public String getSubtitles() {
        return subtitles;
    }
}
