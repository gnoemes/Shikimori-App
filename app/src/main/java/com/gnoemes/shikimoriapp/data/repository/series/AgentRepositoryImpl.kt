package com.gnoemes.shikimoriapp.data.repository.series

import android.content.Context
import com.gnoemes.shikimoriapp.utils.getDefaultSharedPreferences
import javax.inject.Inject


class AgentRepositoryImpl @Inject constructor(
        private val context: Context
) : AgentRepository {

    override fun getAgent(): String {
        return context.getDefaultSharedPreferences().getString("agent", "App") ?: "app"
    }
}