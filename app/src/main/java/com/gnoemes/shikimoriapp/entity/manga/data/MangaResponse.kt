package com.gnoemes.shikimoriapp.entity.manga.data

import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse
import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class MangaResponse(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("russian") val nameRu: String?,
        @field:SerializedName("image") val image: ImageResponse,
        @field:SerializedName("url") val url: String,
        @field:SerializedName("kind") val type: MangaType,
        @field:SerializedName("status") private val _status: Status?,
        @field:SerializedName("volumes") val volumes: Int,
        @field:SerializedName("chapters") val chapters: Int,
        @field:SerializedName("aired_on") val dateAired: DateTime?,
        @field:SerializedName("released_on") val dateReleased: DateTime?
) : LinkedContentResponse() {
    val status: Status
        get() = _status ?: Status.NONE
}