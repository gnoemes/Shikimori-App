package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import com.google.gson.annotations.SerializedName;

public class TranslationResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("addedDateTime")
    private String dateAdded;

    @SerializedName("activeDateTime")
    private String dateActive;

    @SerializedName("qualityType")
    private String quality;

    @SerializedName("title")
    private String title;

    @SerializedName("priority")
    private long priority;

    @SerializedName("type")
    private String type;

    @SerializedName("typeKind")
    private String kind;

    @SerializedName("typeLang")
    private String lang;

    @SerializedName("updatedDateTime")
    private String dateUpdated;

    @SerializedName("url")
    private String url;

    @SerializedName("embedUrl")
    private String embedUrl;

    @SerializedName("authorsSummary")
    private String authors;

    @SerializedName("episode")
    private EpisodeResponse episodeResponse;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("series")
    private SeriesResponse seriesResponses;

    public long getId() {
        return id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDateActive() {
        return dateActive;
    }

    public String getTitle() {
        return title;
    }

    public String getQuality() {
        return quality;
    }

    public long getPriority() {
        return priority;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public String getLang() {
        return lang;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public String getUrl() {
        return url;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public String getAuthors() {
        return authors;
    }

    public EpisodeResponse getEpisodeResponse() {
        return episodeResponse;
    }

    public SeriesResponse getSeriesResponses() {
        return seriesResponses;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
