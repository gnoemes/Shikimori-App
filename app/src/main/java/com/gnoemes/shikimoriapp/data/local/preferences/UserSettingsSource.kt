package com.gnoemes.shikimoriapp.data.local.preferences

import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief


interface UserSettingsSource {

    fun getUser(): UserBrief?

    fun setUser(user: UserBrief)

    fun getUserStatus(): UserStatus

    fun setUserStatus(status: UserStatus)

    fun getAutoStatus(): Boolean

    fun setAutoStatus(status: Boolean)

    fun getRomadziNaming(): Boolean

    fun setRomadziNaming(value: Boolean)

}