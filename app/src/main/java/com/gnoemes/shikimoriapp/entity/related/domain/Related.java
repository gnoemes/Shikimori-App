package com.gnoemes.shikimoriapp.entity.related.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;

public class Related {

    private String relation;
    private String relationRussian;
    private Type type;
    @Nullable
    private Anime anime;
    @Nullable
    private Manga manga;

    public Related(String relation,
                   String relationRussian,
                   Type type,
                   @Nullable Anime anime,
                   @Nullable Manga manga) {
        this.relation = relation;
        this.relationRussian = relationRussian;
        this.type = type;
        this.anime = anime;
        this.manga = manga;
    }

    public String getRelation() {
        return relation;
    }

    public String getRelationRussian() {
        return relationRussian;
    }

    public Type getType() {
        return type;
    }

    @Nullable
    public Anime getAnime() {
        return anime;
    }

    @Nullable
    public Manga getManga() {
        return manga;
    }
}
