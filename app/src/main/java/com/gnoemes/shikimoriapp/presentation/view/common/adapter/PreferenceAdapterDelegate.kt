package com.gnoemes.shikimoriapp.presentation.view.common.adapter

import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

abstract class PreferenceAdapterDelegate<T>(
        private val settings: UserSettingsSource
) : AdapterDelegate<T>() {

    fun isRomadziNaming(): Boolean = settings.getRomadziNaming()

}