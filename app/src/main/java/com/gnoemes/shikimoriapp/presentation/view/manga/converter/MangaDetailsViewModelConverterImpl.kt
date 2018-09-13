package com.gnoemes.shikimoriapp.presentation.view.manga.converter

import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DetailsActionItem
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DetailsCharactersItem
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DetailsContentItem
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaHeadItem
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter
import javax.inject.Inject

class MangaDetailsViewModelConverterImpl @Inject constructor(
        private val dateTimeConverter: DateTimeConverter
) : MangaDetailsViewModelConverter {

    override fun convert(manga: MangaDetails): List<BaseItem> {
        val items = mutableListOf<BaseItem>()

        items.add(MangaHeadItem(
                manga.id,
                manga.name.trim(),
                manga.nameRu?.trim(),
                manga.image,
                manga.url,
                manga.type,
                manga.status,
                dateTimeConverter.convertAnimeSeasonToString(manga.dateAired),
                manga.score,
                manga.volumes,
                manga.chapters,
                manga.genres,
                manga.userRate
        ))

        if (manga.characters.isNotEmpty()) {
            items.add(DoubleDividerItem())
            items.add(DetailsCharactersItem(manga.characters))
        }

        items.add(DoubleDividerItem())
        items.add(DetailsActionItem(DetailsAction.CHRONOLOGY))
        items.add(DetailsActionItem(DetailsAction.LINKS))
        items.add(DoubleDividerItem())

        items.add(DetailsContentItem(manga.id, manga.description))
        items.add(DoubleDividerItem())
        items.add(DetailsActionItem(DetailsAction.COMMENTS))
        items.add(DoubleDividerItem())

        return items
    }
}