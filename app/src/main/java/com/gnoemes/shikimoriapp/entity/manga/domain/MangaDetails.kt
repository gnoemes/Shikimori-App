package com.gnoemes.shikimoriapp.entity.manga.domain

import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import org.joda.time.DateTime

data class MangaDetails(
        val id: Long,
        val name: String,
        val nameRu: String,
        val image: Image,
        val url: String,
        val type: MangaType,
        val status: Status,
        val volumes: Int,
        val chapters: Int,
        val dateAired: DateTime?,
        val dateReleased: DateTime?,
        val score: Double,
        val description: String?,
        val descriptionHtml: String,
        val favoured: Boolean,
        val topicId: Long,
        val genres: List<Genre>,
        val userRate: UserRate?
)