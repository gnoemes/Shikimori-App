package com.gnoemes.shikimoriapp.data.repository.app.impl

import com.gnoemes.shikimoriapp.data.local.preferences.UserPreferenceSource
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
        private val oldSource: UserPreferenceSource,
        private val source: UserSettingsSource
) : UserSettingsRepository {

    override fun getUser(): UserBrief? = source.getUser()
    override fun setUser(user: UserBrief) = source.setUser(user)
    override fun clearUser() = source.clearUser()

    override fun getUserStatus(): UserStatus = source.getUserStatus()
    override fun setUserStatus(status: UserStatus) = source.setUserStatus(status)

    override fun isAutoStatusEnabled(): Boolean = source.getAutoStatus()
    override fun isAutoStatusEnabled(status: Boolean) = source.setAutoStatus(status)

    override fun isRomadziNaming(): Boolean = source.getRomadziNaming()
    override fun isRomadziNaming(enabled: Boolean) = source.setRomadziNaming(enabled)

    override fun isRememberType(): Boolean = source.getRememberType()
    override fun isRememberType(enabled: Boolean) = source.setRememberType(enabled)

    override fun isRememberPlayer(): Boolean = source.getRememberPlayer()
    override fun isRememberPlayer(enabled: Boolean) = source.setRememberPlayer(enabled)

    override fun getTranslationType(): TranslationType = source.getType()
    override fun setTranslationType(type: TranslationType) = source.setType(type)

    override fun getPlayer(): PlayerType = source.getPlayer()
    override fun setPlayer(type: PlayerType) = source.setPlayer(type)

    override fun isNotificationsEnabled(): Boolean = source.getNotificationsEnabled()

    @Deprecated("use non-rx version instead")
    override fun getUserSettings(): Observable<UserSettings> = oldSource.userSettingsObservable

    @Deprecated("use non-rx version instead")
    override fun saveUserSettings(settings: UserSettings): Completable = oldSource.saveUserSettings(settings)
}