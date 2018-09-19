package com.gnoemes.shikimoriapp.data.repository.notifications

import android.app.Notification
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData

interface NotificationConverter {

    fun convert(data: NotificationData, channel: String): Notification
}