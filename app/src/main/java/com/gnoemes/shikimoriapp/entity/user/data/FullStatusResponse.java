package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullStatusResponse {

    @SerializedName("anime")
    List<StatusResponse> animeStasuses;

    @SerializedName("manga")
    List<StatusResponse> mangaStasuses;

    public List<StatusResponse> getAnimeStasuses() {
        return animeStasuses;
    }

    public List<StatusResponse> getMangaStasuses() {
        return mangaStasuses;
    }
}
