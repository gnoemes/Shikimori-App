package com.gnoemes.shikimoriapp.data.repository.notifications

import android.content.SharedPreferences
import com.gnoemes.shikimoriapp.di.app.qualifiers.SettingsQualifier
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.utils.putLong
import org.joda.time.DateTime
import javax.inject.Inject

class NotificationDateSourceImpl @Inject constructor(
        @SettingsQualifier private val sharedPreferences: SharedPreferences
) : NotificationDateSource {

    companion object {
        private const val KEY = "message_date_last"
    }

    override fun getLastDate(): DateTime {
        val mills = sharedPreferences.getLong(KEY, Constants.NO_ID)
        return if (mills == Constants.NO_ID) {
            DateTime.now()
        } else {
            DateTime(mills)
        }
    }

    override fun saveLastDate(dateTime: DateTime) {
        sharedPreferences.putLong(KEY, dateTime.millis)
    }
}