package com.gnoemes.shikimoriapp.domain.series;

import com.gnoemes.shikimoriapp.entity.series.domain.Episode;

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
     * Clear local history
     */
    Completable clearHistory(long animeId);

}
