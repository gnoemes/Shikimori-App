package com.gnoemes.shikimoriapp.entity.rates.domain

import android.os.Parcelable
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
data class UserRate(
        val id: Long? = null,
        val userId: Long? = null,
        val targetId: Long? = null,
        val targetType: Type? = null,
        val score: Double? = null,
        val status: RateStatus? = null,
        val rewatches: Int? = null,
        val episodes: Int? = null,
        val volumes: Int? = null,
        val chapters: Int? = null,
        val text: String? = null,
        val textHtml: String? = null,
        val dateCreated: DateTime? = null,
        val dateUpdated: DateTime? = null
) : Parcelable {
    //for java
    companion object {
        @JvmStatic
        fun getStartWatching(): UserRate = UserRate(Constants.NO_ID, status = RateStatus.WATCHING)
    }
}