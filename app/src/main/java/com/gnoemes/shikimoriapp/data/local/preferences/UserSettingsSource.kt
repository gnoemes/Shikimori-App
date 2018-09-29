package com.gnoemes.shikimoriapp.data.local.preferences

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief


interface UserSettingsSource {

    fun getUser(): UserBrief?

    fun setUser(user: UserBrief)

    fun clearUser()

    fun getUserStatus(): UserStatus

    fun setUserStatus(status: UserStatus)

    fun getAutoStatus(): Boolean

    fun setAutoStatus(status: Boolean)

    fun getRomadziNaming(): Boolean

    fun setRomadziNaming(value: Boolean)

    fun setRememberType(value: Boolean)

    fun getRememberType(): Boolean

    fun setRememberPlayer(value: Boolean)

    fun getRememberPlayer(): Boolean

    fun setType(value: TranslationType)

    fun getType(): TranslationType

    fun setPlayer(value: PlayerType)

    fun getPlayer(): PlayerType

    fun getDownloadLocation(): Int

    fun getNotificationsEnabled(): Boolean
}