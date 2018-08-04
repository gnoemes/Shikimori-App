package com.gnoemes.shikimoriapp.entity.roles.data;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.google.gson.annotations.SerializedName;

public class WorkResponse {

    @SerializedName("anime")
    private AnimeResponse animeResponse;
    @SerializedName("manga")
    private MangaResponse mangaResponse;
    @SerializedName("role")
    private String role;

    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }

    public MangaResponse getMangaResponse() {
        return mangaResponse;
    }

    public String getRole() {
        return role;
    }
}
