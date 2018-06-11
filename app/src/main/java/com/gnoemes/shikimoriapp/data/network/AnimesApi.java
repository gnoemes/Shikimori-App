package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeDetailsResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeFranchiseResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeLinkResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.related.data.RelatedResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface AnimesApi {

    /**
     * Get anime list
     */
    @GET("/api/animes")
    Single<List<AnimeResponse>> getAnimeList(@QueryMap(encoded = true) Map<String, String> filter);

    /**
     * Get anime detail info
     *
     * @param animeId long
     */
    @GET("/api/animes/{id}")
    Single<AnimeDetailsResponse> getAnimeDetails(@Path("id") long animeId);

    /**
     * Get anime external links
     */
    @GET("/api/animes/{id}/external_links")
    Single<List<AnimeLinkResponse>> getAnimeLinks(@Path("id") long animeId);

    @GET("/api/animes/{id}/similar")
    Single<List<AnimeResponse>> getSimilarAnimes(@Path("id") long animeId);

    @GET("/api/animes/{id}/related")
    Single<List<RelatedResponse>> getRelatedItems(@Path("id") long animeId);

    @GET("/api/animes/{id}/franchise")
    Single<AnimeFranchiseResponse> getFranchise(@Path("id") long animeId);
}
