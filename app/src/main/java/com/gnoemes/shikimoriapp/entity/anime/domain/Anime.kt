package com.gnoemes.shikimoriapp.entity.anime.domain

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import org.joda.time.DateTime

data class Anime(
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
        val dateReleased: DateTime?
) : LinkedContent(id, name, LinkedType.ANIME, image.original)