package com.gnoemes.shikimoriapp.presentation.view.manga.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails

interface MangaDetailsViewModelConverter {

    fun convert(manga: MangaDetails): List<BaseItem>
}