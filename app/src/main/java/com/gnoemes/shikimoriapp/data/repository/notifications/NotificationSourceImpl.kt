package com.gnoemes.shikimoriapp.data.repository.notifications

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData
import io.reactivex.Completable
import javax.inject.Inject

//TODO mb grouping
class NotificationSourceImpl @Inject constructor(
        private val notificationManager: NotificationManager,
        private val notificationConverter: NotificationConverter
) : NotificationSource {

    var id: Int = 1

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.gnoemes.shikimoriapp.notification.main"
        const val SERVICE_CHANNEL_ID = "com.gnoemes.shikimoriapp.notification.service"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val mainNotificationChannel = provideMainNotificationChannel()
            val serviceNotificatonChangel = provideServiceNotificationChannel()

            notificationManager.createNotificationChannel(mainNotificationChannel)
            notificationManager.createNotificationChannel(serviceNotificatonChangel)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun provideMainNotificationChannel(): NotificationChannel {
        val mainChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
                "Shikimori App", NotificationManager.IMPORTANCE_DEFAULT)
        mainChannel.enableLights(true)
        mainChannel.enableVibration(true)
        mainChannel.lightColor = Color.WHITE
        mainChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        return mainChannel
    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun provideServiceNotificationChannel(): NotificationChannel {
        val serviceChannel = NotificationChannel(SERVICE_CHANNEL_ID,
                "Shikimori App Service", NotificationManager.IMPORTANCE_DEFAULT)
        serviceChannel.enableLights(true)
        serviceChannel.enableVibration(true)
        serviceChannel.lightColor = Color.WHITE
        serviceChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        return serviceChannel
    }

    override fun createNotification(data: NotificationData): Completable = Completable.fromAction {
        val notification = notificationConverter.convert(data, SERVICE_CHANNEL_ID)
        notificationManager.notify(id++, notification)
    }
}