package com.gnoemes.shikimoriapp.entity.roles.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;

public class Work {
    private Type type;
    @Nullable
    private Anime anime;
    @Nullable
    private Manga manga;
    private String role;

    public Work(Type type, @Nullable Anime anime, @Nullable Manga manga, String role) {
        this.type = type;
        this.anime = anime;
        this.manga = manga;
        this.role = role;
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

    public String getRole() {
        return role;
    }
}
