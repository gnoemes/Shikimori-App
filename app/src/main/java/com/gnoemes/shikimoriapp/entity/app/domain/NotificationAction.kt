package com.gnoemes.shikimoriapp.entity.app.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationAction(
        val id: Long,
        val type: Type
) : Parcelable