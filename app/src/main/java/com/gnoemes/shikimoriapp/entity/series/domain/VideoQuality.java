package com.gnoemes.shikimoriapp.entity.series.domain;

public class VideoQuality {
    private int resolution;
    private String url;

    public VideoQuality(int resolution, String url) {
        this.resolution = resolution;
        this.url = url;
    }

    public int getResolution() {
        return resolution;
    }

    public String getUrl() {
        return url;
    }
}
