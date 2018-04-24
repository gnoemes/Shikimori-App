package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AnimesApi {

    /**
     * Get anime list
     */
    @GET("/api/animes")
    Single<List<AnimeResponse>> getAnimeList(@QueryMap(encoded = true) Map<String, String> filter);

}
