package com.gnoemes.shikimoriapp.data.repository.anime.converter

import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.data.repository.common.ImageResponseConverter
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.utils.appendHostIfNeed
import javax.inject.Inject

class AnimeResponseConverterImpl @Inject constructor(
        private val settingsSource: UserSettingsSource,
        private val imageConverter: ImageResponseConverter
) : AnimeResponseConverter {

    override fun convertFrom(response: AnimeResponse?): Anime? {
        if (response == null) {
            return null
        }

        val isRomandziNaming = settingsSource.getRomadziNaming()
        val name = if (isRomandziNaming) response.name else response.nameRu ?: ""
        val secondName = if (isRomandziNaming) response.nameRu ?: "" else response.name

        return Anime(
                response.id,
                name,
                secondName,
                imageConverter.convert(response.image),
                response.url.appendHostIfNeed(),
                response.type,
                response.status,
                response.episodes,
                response.episodesAired,
                response.dateAired,
                response.dateReleased
        )
    }
}