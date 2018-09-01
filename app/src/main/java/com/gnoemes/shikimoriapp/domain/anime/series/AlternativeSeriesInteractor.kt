package com.gnoemes.shikimoriapp.domain.anime.series

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode
import io.reactivex.Completable
import io.reactivex.Single

interface AlternativeSeriesInteractor {

    /**
     * Get episodes
     */
    fun getEpisodes(animeId: Long): Single<List<Episode>>

    /**
     * Episode was Watched
     */
    fun setEpisodeWatched(animeId: Long, episodeId: Long, rateId: Long = -1): Completable

    /**
     * Get translations for episode
     */
    fun getEpisodeTranslations(type: AlternativeTranslationType, animeId: Long, episodeId: Long): Single<List<AlternativeTranslation>>

    /**
     * Clear local history
     */
    fun clearHistory(animeId: Long): Completable

}