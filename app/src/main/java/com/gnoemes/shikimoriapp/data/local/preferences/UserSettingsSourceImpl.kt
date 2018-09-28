package com.gnoemes.shikimoriapp.data.local.preferences

import android.content.SharedPreferences
import com.gnoemes.shikimoriapp.di.app.qualifiers.SettingsQualifier
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief
import com.gnoemes.shikimoriapp.utils.putBoolean
import com.gnoemes.shikimoriapp.utils.putString
import com.gnoemes.shikimoriapp.utils.remove
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

class UserSettingsSourceImpl @Inject constructor(
        @SettingsQualifier private val prefs: SharedPreferences,
        private val gson: Gson
) : UserSettingsSource {
    override fun getUser(): UserBrief? {
        return try {
            val json = prefs.getString(SettingsExtras.USER_BRIEF, "")
            gson.fromJson<UserBrief>(json, UserBrief::class.java)
        } catch (e: JsonSyntaxException) {
            throw IllegalStateException("User doesn't exist")
        }

    }

    override fun setUser(user: UserBrief) {
        prefs.putString(SettingsExtras.USER_BRIEF, gson.toJson(user))
    }

    override fun getUserStatus(): UserStatus {
        val value = prefs.getString(SettingsExtras.USER_STATUS, "")
        return try {
            UserStatus.valueOf(value!!.toUpperCase())
        } catch (e: Exception) {
            UserStatus.GUEST
        }
    }

    override fun clearUser() {
        prefs.remove(SettingsExtras.USER_BRIEF)
        setUserStatus(UserStatus.GUEST)
    }

    override fun setUserStatus(status: UserStatus) {
        prefs.putString(SettingsExtras.USER_STATUS, status.status)
    }

    override fun getAutoStatus(): Boolean =
            prefs.getBoolean(SettingsExtras.AUTO_STATUS, true)

    override fun setAutoStatus(status: Boolean) {
        prefs.putBoolean(SettingsExtras.AUTO_STATUS, status)
    }

    override fun getRomadziNaming(): Boolean =
            prefs.getBoolean(SettingsExtras.IS_ROMADZI_NAMING, false)

    override fun setRomadziNaming(value: Boolean) {
        prefs.putBoolean(SettingsExtras.IS_ROMADZI_NAMING, value)
    }

    override fun setRememberType(value: Boolean) {
        prefs.putBoolean(SettingsExtras.TRANSLATION_TYPE_REMEMBER, value)
    }

    override fun getRememberType(): Boolean =
            prefs.getBoolean(SettingsExtras.TRANSLATION_TYPE_REMEMBER, false)

    override fun setType(value: TranslationType) {
        prefs.putString(SettingsExtras.TRANSLATION_TYPE, value.type)
    }

    override fun getType(): TranslationType {
        val type = prefs.getString(SettingsExtras.TRANSLATION_TYPE, "")
        return TranslationType.values().find { it.isEqualType(type) } ?: TranslationType.ALL
    }

    override fun getDownloadLocation(): Int = prefs.getInt(SettingsExtras.DOWNLOAD_LOCATION_TYPE, 1)

    override fun getNotificationsEnabled(): Boolean =
            prefs.getBoolean(SettingsExtras.IS_NOTIFICATIONS_ENABLED, true)

}