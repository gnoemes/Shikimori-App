package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Genre;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;

import java.util.List;

public class AnimeHeadItem extends BaseAnimeItem {

    private long id;
    private String title;
    private String subTitle;
    private String url;
    private String imageUrl;
    private String animeType;
    private String animeStatus;
    private String season;
    private List<Genre> genres;
    private double score;
    @Nullable
    private UserRate animeRate;

    public AnimeHeadItem(long id, String title, String subTitle,
                         String url, String imageUrl, String animeType,
                         String animeStatus, String season, List<Genre> genres,
                         double score, @Nullable UserRate animeRate) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.url = url;
        this.imageUrl = imageUrl;
        this.animeType = animeType;
        this.animeStatus = animeStatus;
        this.season = season;
        this.genres = genres;
        this.score = score;
        this.animeRate = animeRate;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public double getScore() {
        return score;
    }

    @Nullable
    public UserRate getAnimeRate() {
        return animeRate;
    }
}

