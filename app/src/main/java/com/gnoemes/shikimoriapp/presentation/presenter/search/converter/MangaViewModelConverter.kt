package com.gnoemes.shikimoriapp.presentation.presenter.search.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem

interface MangaViewModelConverter {

    fun convertListFrom(mangas: List<Manga>?): List<BaseItem>

    fun convertManga(manga: Manga): BaseSearchItem
}