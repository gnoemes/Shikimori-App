package com.gnoemes.shikimoriapp.domain.anime.series;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SeriesInteractor {

    /**
     * Get episodes
     */
    Single<Series> getEpisodes(long animeId);

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
    Single<List<Translation>> getEpisodeTranslations(TranslationType type, long animeId, int episodeId);

    /**
     * Clear local history
     */
    Completable clearHistory(long animeId);

    /**
     * Returns url of embedded video
     */
    Single<PlayVideo> getVideo(long animeId, int episodeId, long videoId);

    /**
     * Returns url of embedded video
     */
    Single<PlayVideo> getVideo(long animeId, int episodeId);

    /**
     * Returns video source
     */
    Single<PlayVideo> getVideoSource(long animeId, int currentEpisode);

    /**
     * Returns video source
     */
    Single<PlayVideo> getVideoSource(long animeId, int episodeId, long videoId);
}
