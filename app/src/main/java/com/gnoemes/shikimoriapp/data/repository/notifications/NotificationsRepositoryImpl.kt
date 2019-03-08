package com.gnoemes.shikimoriapp.data.repository.notifications

import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData
import io.reactivex.Completable
import org.joda.time.DateTime
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
        private val notificationSource: NotificationSource,
        private val dateSource: NotificationDateSource
) : NotificationsRepository {

    override fun createNotification(data: NotificationData): Completable = notificationSource.createNotification(data)

    override fun lastNewsMessageDate(): Long = dateSource.getLastDateMills()

    override fun saveNewsMessageDate(dateTime: DateTime?) = dateSource.saveLastDate(dateTime)
}