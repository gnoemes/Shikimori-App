package com.gnoemes.shikimoriapp.domain.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjection
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationsInteractor: NotificationsInteractor

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)
        //TODO tags and several jobs
        syncAnimeNotifications()
    }

    private fun syncAnimeNotifications() {
        val disposable = notificationsInteractor.syncAnimeNotifications()
                .subscribe({}, { Crashlytics.logException(it) })

    }
}