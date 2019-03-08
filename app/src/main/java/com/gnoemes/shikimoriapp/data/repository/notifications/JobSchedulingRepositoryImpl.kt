package com.gnoemes.shikimoriapp.data.repository.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.gnoemes.shikimoriapp.domain.notifications.NotificationReceiver
import com.gnoemes.shikimoriapp.entity.app.domain.JobKey
import com.gnoemes.shikimoriapp.utils.alarmManager
import io.reactivex.Completable
import javax.inject.Inject

class JobSchedulingRepositoryImpl @Inject constructor(
        private val content: Context
) : JobSchedulingRepository {

    override fun planAnimeEpisodesNotifications(): Completable = Completable.fromAction {
        val alarmManager = content.alarmManager()

        val intent = getPendingIntent(JobKey.ANIME_EPISODE_NOTIFICATIONS)
        val mills = 5 * 60 * 1000L
        alarmManager.cancel(intent)
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), mills, intent)
    }

    override fun cancelEpisodesNotifications(tag: String): Completable {
        return Completable.fromAction {
            val pendingIntent = getPendingIntent(tag)
            content.alarmManager().cancel(pendingIntent)
        }
    }

    private fun getPendingIntent(tag: String): PendingIntent {
        val intent = Intent(content, NotificationReceiver::class.java)
        intent.putExtra(JobKey.NOTIFICATIONS_KEY, tag)
        return PendingIntent.getBroadcast(content, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}