package com.gnoemes.shikimoriapp.entity.search.presentation

import android.os.Parcelable
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchNavigationData(
        val genre: Genre,
        val type: Type
) : Parcelable