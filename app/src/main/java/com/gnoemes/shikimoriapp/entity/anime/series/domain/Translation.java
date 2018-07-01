package com.gnoemes.shikimoriapp.entity.anime.series.domain;

public class Translation {

    private long id;
    private TranslationQuality quality;
    private String title;
    private long priority;
    private TranslationType type;
    private String url;
    private String embedUrl;
    private Episode episode;
    private int width;
    private int height;
    private String authors;
    private String serialTitle;

    public Translation(long id, TranslationQuality quality, String title,
                       long priority, TranslationType type, String url,
                       String embedUrl, Episode episode, int width,
                       int height, String authors, String serialTitle) {
        this.id = id;
        this.quality = quality;
        this.title = title;
        this.priority = priority;
        this.type = type;
        this.url = url;
        this.embedUrl = embedUrl;
        this.episode = episode;
        this.width = width;
        this.height = height;
        this.authors = authors;
        this.serialTitle = serialTitle;
    }

    public long getId() {
        return id;
    }

    public TranslationQuality getQuality() {
        return quality;
    }

    public long getPriority() {
        return priority;
    }

    public TranslationType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public Episode getEpisode() {
        return episode;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getSerialTitle() {
        return serialTitle;
    }
}
