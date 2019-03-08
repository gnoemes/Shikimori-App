package com.gnoemes.shikimoriapp.entity.search.presentation

import android.os.Parcelable
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimilarNavigationData(
        val id: Long,
        val type: Type
) : Parcelable