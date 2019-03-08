package com.gnoemes.shikimoriapp.entity.manga.presentation

import android.os.Parcelable
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MangaNavigationData(
        val id: Long,
        val type: Type
) : Parcelable