package com.gnoemes.shikimoriapp.presentation.presenter.search.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaViewModel
import javax.inject.Inject

class MangaViewModelConverterImpl @Inject constructor(

) : MangaViewModelConverter {


    override fun convertListFrom(mangas: List<Manga>?): List<BaseItem> {
        if (mangas == null) {
            return emptyList()
        }

        return mangas.map { convertManga(it) }
    }

    override fun convertManga(manga: Manga): MangaViewModel =
            MangaViewModel(
                    manga.id,
                    manga.name,
                    manga.nameRu,
                    manga.image,
                    manga.url,
                    manga.type,
                    manga.status,
                    manga.volumes,
                    manga.chapters,
                    manga.dateAired,
                    manga.dateReleased,
                    manga.isRanobe
            )
}