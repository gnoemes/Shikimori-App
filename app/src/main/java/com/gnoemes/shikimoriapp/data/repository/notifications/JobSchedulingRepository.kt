package com.gnoemes.shikimoriapp.data.repository.notifications

import io.reactivex.Completable

interface JobSchedulingRepository {

    fun planAnimeEpisodesNotifications(): Completable

    fun cancelEpisodesNotifications(tag: String): Completable
}