package com.gnoemes.shikimoriapp.domain.anime.series;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SeriesInteractor {

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
    Single<List<Translation>> getEpisodeTranslations(TranslationType type, long episodeId);

    /**
     * Get translation by type and order by rating
     */
    Single<Translation> getAutoTranslation(TranslationType type, long episodeId);

    /**
     * Clear local history
     */
    Completable clearHistory(long animeId);

}
