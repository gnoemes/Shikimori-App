package com.gnoemes.shikimoriapp.data.repository.anime.series;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

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
    Single<List<Episode>> getAnimeEpisodes(long animeId);

    /**
     * Get translations by episodeId
     */
    Single<List<Translation>> getTranslations(TranslationType type, long episodeId);

    /**
     * Saves episode to history
     */
    Completable setEpisodeWatched(long animeId, long episodeId);
}
