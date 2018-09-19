package com.gnoemes.shikimoriapp.domain.notifications

import com.gnoemes.shikimoriapp.data.repository.notifications.JobSchedulingRepository
import com.gnoemes.shikimoriapp.entity.app.domain.JobKey
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import io.reactivex.Completable
import javax.inject.Inject

class JobSchedulingInteractorImpl @Inject constructor(
        private val repository: JobSchedulingRepository,
        private val completableErrorHandler: CompletableErrorHandler,
        private val rxUtils: RxUtils
) : JobSchedulingInteractor {

    override fun planAnimeEpisodesNotifications(): Completable =
            repository.planAnimeEpisodesNotifications()
                    .compose(completableErrorHandler)
                    .compose(rxUtils.applyCompleteSchedulers())

    override fun cancelAnimeNotifications(): Completable =
            repository.cancelEpisodesNotifications(JobKey.ANIME_EPISODE_NOTIFICATIONS)
                    .compose(completableErrorHandler)
                    .compose(rxUtils.applyCompleteSchedulers())
}