package com.gnoemes.shikimoriapp.entity.anime.domain;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

/**
 * Anime model
 */
public class Anime {

    private long id;

    private String name;

    @Nullable
    private String russianName;

    private AnimeImage animeImage;

    private String url;

    private AnimeType type;

    private AnimeStatus status;

    private int episodes;

    private int episodesAired;

    private DateTime airedDate;

    @Nullable
    private DateTime releasedDate;

    public Anime(long id, String name, @Nullable String russianName, AnimeImage animeImage,
                 String url, AnimeType type, AnimeStatus status, int episodes,
                 int episodesAired, DateTime airedDate, @Nullable DateTime releasedDate) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.animeImage = animeImage;
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

    public AnimeImage getAnimeImage() {
        return animeImage;
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
