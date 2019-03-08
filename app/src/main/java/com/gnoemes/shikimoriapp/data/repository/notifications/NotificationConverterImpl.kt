package com.gnoemes.shikimoriapp.data.repository.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationAction
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationType
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.user.domain.Message
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import javax.inject.Inject


class NotificationConverterImpl @Inject constructor(
        private val context: Context,
        private val settingsInteractor: UserSettingsInteractor,
        private val analyticsInteractor: AnalyticsInteractor,
        private val imageLoader: ImageLoader
) : NotificationConverter {

    override fun convert(data: NotificationData, channel: String): Notification {
        return when (data.type) {
            NotificationType.NEW_EPISODE -> createNewEpisodeNotification(data, channel)
            NotificationType.ANIME_ONGOING -> createAnimeOngoingNotification(data, channel)
            NotificationType.ANIME_RELEASE -> createAnimeReleaseNotififcation(data, channel)
        }
    }

    private fun createAnimeReleaseNotififcation(data: NotificationData, channel: String): Notification {
        val anime = (data.payload as? Anime)!!
        val title = context.getString(R.string.notification_anime_release_title)
        val text = String.format(context.getString(R.string.notification_anime_release_format), anime.name)

        return animeNotification(anime, title, text, channel)
    }

    private fun createAnimeOngoingNotification(data: NotificationData, channel: String): Notification {
        val anime = (data.payload as? Anime)!!
        val title = context.getString(R.string.notification_anime_ongoing_title)
        val text = String.format(context.getString(R.string.notification_anime_ongoing_format), anime.name)
        return animeNotification(anime, title, text, channel)
    }

    private fun createNewEpisodeNotification(data: NotificationData, channel: String): Notification {
        val message = (data.payload as? Message)!!
        val title = context.getString(R.string.notification_new_episode_title)
        val text = message.htmlBody
        return animeNotification(message.linked as Anime, title, text, channel)
    }

    private fun animeNotification(anime: Anime, title: String, text: String, channel: String): Notification {
        val intent = Intent(context, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            it.putExtra(AppExtras.ARGUMENT_NOTIFICATION_ACTION, NotificationAction(anime.id, Type.ANIME))
        }

        val contentIntent: PendingIntent = PendingIntent.getActivity(context, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pattern = longArrayOf(0, 250, 250, 250)


        return NotificationCompat.Builder(context, channel)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setVibrate(pattern)
                .setLights(Color.WHITE, 2000, 3000)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setGroup("anime_notification_group")
                .setGroupSummary(true)
                .build()
    }
}