package com.gnoemes.shikimoriapp.data.repository.series;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
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
    Single<List<Translation>> getTranslations(TranslationType type, long animeId, int episodeId);

    /**
     * Saves episode to history
     */
    Completable setEpisodeWatched(long animeId, long episodeId);

    /**
     * Checks is episode watched
     */
    Single<Boolean> isEpisodeWatched(long animeId, long episodeId);

    /**
     * Clear local history
     */
    Completable clearHistory(long animeId);

    /**
     * Returns base web embedded player url
     */
    Single<PlayVideo> getVideo(long animeId, int episodeId, long videoId);

    /**
     * Returns base web embedded player url
     */
    Single<PlayVideo> getVideo(long animeId, int episodeId);

    /**
     * Returns video source
     */
    Single<PlayVideo> getVideoSource(long animeId, int episodeId, long videoId);

    /**
     * Returns video source
     */
    Single<PlayVideo> getVideoSource(long animeId, int episodeId);
}
