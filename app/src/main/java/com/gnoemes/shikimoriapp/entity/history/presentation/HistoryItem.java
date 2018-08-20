package com.gnoemes.shikimoriapp.entity.history.presentation;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

import org.joda.time.DateTime;

public class HistoryItem extends BaseHistoryItem {

    private long id;
    private String name;
    private String secondName;
    private String imageOriginalUrl;
    private String imagePreviewUrl;
    private String imageX96Url;
    private String imageX48Url;
    private String url;
    private AnimeType type;
    private AnimeStatus status;
    private int episodes;
    private int episodesAired;
    private DateTime airedDate;
    @Nullable
    private DateTime releasedDate;

    public HistoryItem(long id, String name, String secondName,
                       String imageOriginalUrl, String imagePreviewUrl, String imageX96Url,
                       String imageX48Url, String url, AnimeType type, AnimeStatus status,
                       int episodes, int episodesAired, DateTime airedDate,
                       @Nullable DateTime releasedDate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.imageOriginalUrl = imageOriginalUrl;
        this.imagePreviewUrl = imagePreviewUrl;
        this.imageX96Url = imageX96Url;
        this.imageX48Url = imageX48Url;
        this.url = url;
        this.type = type;
        this.status = status;
        this.episodes = episodes;
        this.episodesAired = episodesAired;
        this.airedDate = airedDate;
        this.releasedDate = releasedDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getImageOriginalUrl() {
        return imageOriginalUrl;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public String getImageX96Url() {
        return imageX96Url;
    }

    public String getImageX48Url() {
        return imageX48Url;
    }

    public String getUrl() {
        return url;
    }

    public AnimeType getType() {
        return type;
    }

    public AnimeStatus getStatus() {
        return status;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getEpisodesAired() {
        return episodesAired;
    }

    public DateTime getAiredDate() {
        return airedDate;
    }

    @Nullable
    public DateTime getReleasedDate() {
        return releasedDate;
    }

}
