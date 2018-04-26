package com.gnoemes.shikimoriapp.entity.anime.presentation;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;

import java.util.List;

/**
 * View model for full anime info
 */
public class AnimeDetailsViewModel {

    private long id;
    private String name;
    private String jpOrEngName;
    private String url;
    private String imageUrl;
    private String animeType;
    private String animeStatus;
    private List<AnimeGenre> genres;
    private int episodes;
    private int episodesAired;
    private String season;
    private int duration;
    private double score;
    @Nullable
    private String description;

    public AnimeDetailsViewModel(long id, String name, String jpOrEngName, String url,
                                 String imageUrl, String animeType, String animeStatus,
                                 List<AnimeGenre> genres, int episodes, int episodesAired,
                                 String season, int duration, double score, @Nullable String description) {
        this.id = id;
        this.name = name;
        this.jpOrEngName = jpOrEngName;
        this.url = url;
        this.imageUrl = imageUrl;
        this.animeType = animeType;
        this.animeStatus = animeStatus;
        this.genres = genres;
        this.episodes = episodes;
        this.episodesAired = episodesAired;
        this.season = season;
        this.duration = duration;
        this.score = score;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJpOrEngName() {
        return jpOrEngName;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAnimeType() {
        return animeType;
    }

    public String getAnimeStatus() {
        return animeStatus;
    }

    public List<AnimeGenre> getGenres() {
        return genres;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getEpisodesAired() {
        return episodesAired;
    }

    public String getSeason() {
        return season;
    }

    public int getDuration() {
        return duration;
    }

    public double getScore() {
        return score;
    }

    @Nullable
    public String getDescription() {
        return description;
    }
}