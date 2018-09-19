package com.gnoemes.shikimoriapp.domain.notifications

import io.reactivex.Completable

interface NotificationsInteractor {

    fun syncAnimeNotifications(): Completable
}