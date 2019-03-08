package com.gnoemes.shikimoriapp.entity.manga.presentation

import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem
import org.joda.time.DateTime

data class MangaViewModel(
        val id: Long,
        val name: String,
        val nameRu: String?,
        val image: Image,
        val url: String,
        val type: MangaType,
        val status: Status,
        val volumes: Int,
        val chapters: Int,
        val dateAired: DateTime?,
        val dateReleased: DateTime?,
        val isRanobe: Boolean = type == MangaType.NOVEL
) : BaseSearchItem()