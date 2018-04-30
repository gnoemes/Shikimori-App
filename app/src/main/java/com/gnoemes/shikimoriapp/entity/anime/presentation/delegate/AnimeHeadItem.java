package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;

import java.util.List;

public class AnimeHeadItem extends BaseAnimeItem {

    private long id;
    private String name;
    private String jpOrEngName;
    private String url;
    private String imageUrl;
    private String animeType;
    private String animeStatus;
    private String season;
    private List<AnimeGenre> genres;
    private double score;

    public AnimeHeadItem(long id, String name, String jpOrEngName,
                         String url, String imageUrl, String animeType,
                         String animeStatus, String season, List<AnimeGenre> genres,
                         double score) {
        this.id = id;
        this.name = name;
        this.jpOrEngName = jpOrEngName;
        this.url = url;
        this.imageUrl = imageUrl;
        this.animeType = animeType;
        this.animeStatus = animeStatus;
        this.season = season;
        this.genres = genres;
        this.score = score;
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

    public String getSeason() {
        return season;
    }

    public List<AnimeGenre> getGenres() {
        return genres;
    }

    public double getScore() {
        return score;
    }
}

