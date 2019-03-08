package com.gnoemes.shikimoriapp.data.repository.notifications

import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData
import io.reactivex.Completable

interface NotificationSource {
    fun createNotification(data: NotificationData): Completable
}