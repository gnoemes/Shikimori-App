package com.gnoemes.shikimoriapp.entity.anime.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;
import com.gnoemes.shikimoriapp.entity.common.domain.Image;

import org.joda.time.DateTime;

/**
 * Anime model
 */
public class Anime extends LinkedContent {

    private long id;

    private String name;

    @Nullable
    private String secondName;

    private Image image;

    private String url;

    private AnimeType type;

    private Status status;

    private int episodes;

    private int episodesAired;

    private DateTime airedDate;

    @Nullable
    private DateTime releasedDate;

    public Anime(long id, String name, @Nullable String secondName, Image image,
                 String url, AnimeType type, Status status, int episodes,
                 int episodesAired, DateTime airedDate, @Nullable DateTime releasedDate) {
        super(id, LinkedType.ANIME, image.getOriginal());
        this.id = id;
        this.name = name;
        this.secondName = secondName;
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
    public String getSecondName() {
        return secondName;
    }

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public AnimeType getType() {
        return type;
    }

    public Status getStatus() {
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
