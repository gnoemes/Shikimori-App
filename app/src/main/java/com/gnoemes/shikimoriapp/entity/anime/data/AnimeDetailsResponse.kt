package com.gnoemes.shikimoriapp.entity.anime.data

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType
import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateResponse
import com.gnoemes.shikimoriapp.entity.video.data.VideoResponse
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class AnimeDetailsResponse(
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
        @field:SerializedName("released_on") val dateReleased: DateTime?,
        @field:SerializedName("rating") val rating: String,
        @field:SerializedName("english") val namesEnglish: List<String?>?,
        @field:SerializedName("japanese") val namesJapanese: List<String?>?,
        @field:SerializedName("duration") val duration: Int,
        @field:SerializedName("score") val score: Double,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("description_html") val descriptionHtml: String,
        @field:SerializedName("favoured") val isFavorite: Boolean,
        @field:SerializedName("topic_id") val topicId: Long,
        @field:SerializedName("genres") val genres: List<GenreResponse>,
        @field:SerializedName("videos") val videos: List<VideoResponse>,
        @field:SerializedName("user_rate") val userRate: UserRateResponse?
) {

    val status
        get() = _status ?: AnimeStatus.NONE

}