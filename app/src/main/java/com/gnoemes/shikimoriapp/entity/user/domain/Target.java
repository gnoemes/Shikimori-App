package com.gnoemes.shikimoriapp.entity.user.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.common.domain.Image;

import org.joda.time.DateTime;

public class Target {
    private long id;

    private String name;

    @Nullable
    private String russianName;

    private Image image;

    private String url;

    private TargetType type;

    private AnimeStatus status;

    private int episodes;

    private int episodesAired;

    private DateTime airedDate;

    @Nullable
    private DateTime releasedDate;

    public Target(long id, String name, @Nullable String russianName, Image image,
                  String url, TargetType type, AnimeStatus status, int episodes,
                  int episodesAired, DateTime airedDate, @Nullable DateTime releasedDate) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.image = image;
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

    @Nullable
    public String getRussianName() {
        return russianName;
    }

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public TargetType getType() {
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
