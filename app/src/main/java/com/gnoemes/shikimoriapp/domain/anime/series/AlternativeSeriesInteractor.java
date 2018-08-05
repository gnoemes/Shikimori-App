package com.gnoemes.shikimoriapp.domain.anime.series;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface AlternativeSeriesInteractor {

    /**
     * Get episodes
     */
    Single<List<Episode>> getEpisodes(long animeId);

    /**
     * Episode was Watched
     */
    Completable setEpisodeWatched(long animeId, long episodeId, long rateId);


    /**
     * Episode was Watched
     */
    Completable setEpisodeWatched(long animeId, long episodeId);

    /**
     * Get translations for episode
     */
    Single<List<AlternativeTranslation>> getEpisodeTranslations(AlternativeTranslationType type, long animeId, long episodeId);

    /**
     * Clear local history
     */
    Completable clearHistory(long animeId);
}
