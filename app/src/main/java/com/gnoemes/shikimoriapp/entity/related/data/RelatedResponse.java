package com.gnoemes.shikimoriapp.entity.related.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.google.gson.annotations.SerializedName;

public class RelatedResponse {

    @SerializedName("relation")
    private String relation;
    @SerializedName("relation_russian")
    private String relationRussian;
    @SerializedName("anime")
    @Nullable
    private AnimeResponse animeResponse;
    @SerializedName("manga")
    @Nullable
    private MangaResponse mangaResponse;

    public String getRelation() {
        return relation;
    }

    public String getRelationRussian() {
        return relationRussian;
    }

    @Nullable
    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }

    @Nullable
    public MangaResponse getMangaResponse() {
        return mangaResponse;
    }
}
