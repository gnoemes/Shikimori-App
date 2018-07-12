package com.gnoemes.shikimoriapp.entity.series.domain;

public class VideoTrack {
    private int resolution;
    private String url;
    private VideoFormat format;

    public VideoTrack(int resolution,
                      String url,
                      VideoFormat format) {
        this.resolution = resolution;
        this.url = url;
        this.format = format;
    }

    public int getResolution() {
        return resolution;
    }

    public String getUrl() {
        return url;
    }

    public VideoFormat getFormat() {
        return format;
    }
}
