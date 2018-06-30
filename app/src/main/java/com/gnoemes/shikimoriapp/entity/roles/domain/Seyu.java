package com.gnoemes.shikimoriapp.entity.roles.domain;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;

public class Seyu {
    private long id;
    private String name;
    private String russianName;
    private AnimeImage imageResponse;
    private String url;

    public Seyu(long id, String name, String russianName, AnimeImage imageResponse, String url) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.imageResponse = imageResponse;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public AnimeImage getImageResponse() {
        return imageResponse;
    }

    public String getUrl() {
        return url;
    }
}
