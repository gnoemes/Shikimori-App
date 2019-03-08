package com.gnoemes.shikimoriapp.data.repository.notifications

import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData
import io.reactivex.Completable
import org.joda.time.DateTime

interface NotificationsRepository {

    fun lastNewsMessageDate(): Long

    fun createNotification(data: NotificationData): Completable

    fun saveNewsMessageDate(dateTime: DateTime?)
}