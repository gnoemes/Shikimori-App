package com.gnoemes.shikimoriapp.data.repository.series;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Repository for series, episodes, translations
 */
public interface SeriesRepository {

    /**
     * Get anime series by MyAnimeList id
     */
    Single<Series> getAnimeSeries(long animeId);

    /**
     * Get translations by episodeId
     */
    Single<List<Translation>> getTranslations(TranslationType type, long episodeId);

    /**
     * Get translation by id
     */
    Single<Translation> getTranslation(long translationId);

    Single<List<PlayEpisode>> getTranslationVideoRawData(long translationId);

    /**
     * Saves episode to history
     */
    Completable setEpisodeWatched(long animeId, long episodeId);

    Single<Boolean> isEpisodeWatched(long episodeId);

    /**
     * Clear local history
     */
    Completable clearHistory(long animeId);
}
