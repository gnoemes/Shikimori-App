package com.gnoemes.shikimoriapp.data.local.preferences

import android.content.SharedPreferences
import com.gnoemes.shikimoriapp.di.app.qualifiers.SettingsQualifier
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief
import com.gnoemes.shikimoriapp.utils.putBoolean
import com.gnoemes.shikimoriapp.utils.putString
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
            null
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
}