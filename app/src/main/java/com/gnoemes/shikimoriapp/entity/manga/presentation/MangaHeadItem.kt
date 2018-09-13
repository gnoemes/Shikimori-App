package com.gnoemes.shikimoriapp.entity.manga.presentation

import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate

data class MangaHeadItem(
        val id: Long,
        val name: String,
        val nameRu: String?,
        val image: Image,
        val url: String,
        val type: MangaType,
        val status: Status,
        val season: String,
        val score: Double,
        val volumes: Int,
        val chapters: Int,
        val genres: List<Genre>,
        val userRate: UserRate?
) : BaseItem()