package com.gnoemes.shikimoriapp.data.repository.notifications

import org.joda.time.DateTime

interface NotificationDateSource {

    fun getLastDate(): DateTime

    fun saveLastDate(dateTime: DateTime)
}