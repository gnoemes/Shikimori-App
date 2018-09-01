package com.gnoemes.shikimoriapp.domain.anime.series

import com.gnoemes.shikimoriapp.data.repository.alternative.AlternativeRepository
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AlternativeSeriesInteractorImpl @Inject constructor(
        private val repository: AlternativeRepository,
        private val ratesRepository: UserRatesRepository,
        private val settingsRepository: UserSettingsRepository,
        private val singleErrorHandler: SingleErrorHandler<Any>,
        private val completableErrorHandler: CompletableErrorHandler,
        private val rxUtils: RxUtils
) : AlternativeSeriesInteractor {


    override fun getEpisodes(animeId: Long): Single<List<Episode>> {
        return repository.getAnimeEpisodes(animeId)
                .compose(singleErrorHandler as SingleErrorHandler<List<Episode>>)
                .compose(rxUtils.applySingleSchedulers())
    }

    override fun setEpisodeWatched(animeId: Long, episodeId: Long, rateId: Long): Completable {
        return repository.isEpisodeWatched(animeId, episodeId)
                .flatMapCompletable { isWatched ->
                    if (!isWatched) {
                        repository.setEpisodeWatched(animeId, episodeId)
                                .andThen(createRateIfNeed(animeId))
                                .andThen(if (rateId != Constants.NO_ID) ratesRepository.onEpisodeWatched(rateId) else Completable.complete())
                    } else {
                        Completable.complete()
                    }
                }
    }

    override fun getEpisodeTranslations(type: AlternativeTranslationType, animeId: Long, episodeId: Long): Single<List<AlternativeTranslation>> =
            repository.getTranslations(type, animeId, episodeId)
                    .compose(singleErrorHandler as SingleErrorHandler<List<AlternativeTranslation>>)
                    .compose(rxUtils.applySingleSchedulers())

    override fun clearHistory(animeId: Long): Completable =
            repository.clearHistory(animeId)
                    .compose(completableErrorHandler)
                    .compose(rxUtils.applyCompleteSchedulers<Any>())

    private fun createRateIfNeed(animeId: Long): Completable {
        return settingsRepository.userSettings
                .filter(UserSettings::getAutoStatus)
                .filter { it.userBrief != null }
                .flatMapCompletable { ratesRepository.createRate(animeId, Type.ANIME, UserRate.getStartWatching(), it.userBrief.id) }
    }
}