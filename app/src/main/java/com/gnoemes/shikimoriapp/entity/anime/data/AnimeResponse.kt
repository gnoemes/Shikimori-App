package com.gnoemes.shikimoriapp.entity.anime.data

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType
import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse
import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class AnimeResponse(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("russian") val nameRu: String?,
        @field:SerializedName("image") val image: ImageResponse,
        @field:SerializedName("url") val url: String,
        @field:SerializedName("kind") val type: AnimeType,
        @field:SerializedName("status") private val _status: AnimeStatus?,
        @field:SerializedName("episodes") val episodes: Int,
        @field:SerializedName("episodes_aired") val episodesAired: Int,
        @field:SerializedName("aired_on") val dateAired: DateTime?,
        @field:SerializedName("released_on") val dateReleased: DateTime?
) : LinkedContentResponse() {

    val status: AnimeStatus
        get() = _status ?: AnimeStatus.NONE
}