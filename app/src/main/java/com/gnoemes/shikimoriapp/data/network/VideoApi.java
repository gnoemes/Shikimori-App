package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.anime.data.SeriesDataResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Api for video hosting
 */
public interface VideoApi {

    /**
     * Get series of anime by MyAnimeList id
     */
    @GET("/api/series")
    Single<SeriesDataResponse> getAnimeSeriesById(@Query("myAnimeListId") long animeId);

    /**
     * Get translations of episode
     */
    @GET("/api/translations")
    Single<TranslationListResponse> getEpisodeTranslations(@Query("type") String type, @Query("episodeId") long id);
}
