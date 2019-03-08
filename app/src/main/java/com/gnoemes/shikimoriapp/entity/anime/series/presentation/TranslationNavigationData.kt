package com.gnoemes.shikimoriapp.entity.anime.series.presentation

import android.os.Parcelable
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TranslationNavigationData(val animeId: Long,
                                     val episodeId: Int,
                                     val rateId: Long,
                                     val type: TranslationType
) : Parcelable