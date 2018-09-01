package com.gnoemes.shikimoriapp.entity.anime.domain

import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.entity.roles.domain.Character
import com.gnoemes.shikimoriapp.entity.video.domain.Video
import org.joda.time.DateTime

data class AnimeDetails(
        val id: Long,
        val name: String,
        val secondName: String,
        val image: Image,
        val url: String,
        val type: AnimeType,
        val status: AnimeStatus,
        val episodes: Int,
        val episodesAired: Int,
        val dateAired: DateTime?,
        val releasedDate: DateTime?,
        val englishNames: MutableList<String?>?,
        val japaneseNames: MutableList<String?>?,
        val duration: Int,
        val score: Double,
        val description: String?,
        val isFavorite: Boolean,
        val topicId: Long,
        val genres: MutableList<AnimeGenre>,
        val videos: MutableList<Video>,
        val userRate: UserRate?,
        val characters: MutableList<Character>
)