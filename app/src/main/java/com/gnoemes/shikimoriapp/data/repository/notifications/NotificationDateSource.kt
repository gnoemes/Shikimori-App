package com.gnoemes.shikimoriapp.data.repository.notifications

import org.joda.time.DateTime

interface NotificationDateSource {

    fun getLastDateMills(): Long

    fun saveLastDate(dateTime: DateTime?)
}