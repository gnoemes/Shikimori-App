package com.gnoemes.shikimoriapp.domain.notifications

import io.reactivex.Completable

interface JobSchedulingInteractor {

    fun planAnimeEpisodesNotifications(): Completable

    fun cancelAnimeNotifications(): Completable
}