package com.gnoemes.shikimoriapp.entity.manga.domain

import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import org.joda.time.DateTime

data class Manga(
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
) : LinkedContent(id, LinkedType.MANGA, image.original)