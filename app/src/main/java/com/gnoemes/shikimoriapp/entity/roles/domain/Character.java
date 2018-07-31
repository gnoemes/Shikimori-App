package com.gnoemes.shikimoriapp.entity.roles.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;

public class Character extends LinkedContent {

    private long id;
    private String name;
    @Nullable
    private String russianName;
    private AnimeImage animeImage;
    private String url;

    public Character(long id, String name, @Nullable String russianName,
                     AnimeImage animeImage, String url) {
        super(id, LinkedType.CHARACTER, animeImage.getImageOriginalUrl());
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.animeImage = animeImage;
        this.url = url;
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
}
