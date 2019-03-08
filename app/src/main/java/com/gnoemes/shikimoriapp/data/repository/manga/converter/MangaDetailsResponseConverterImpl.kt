package com.gnoemes.shikimoriapp.data.repository.manga.converter

import com.gnoemes.shikimoriapp.data.repository.anime.converter.RolesResponseConverter
import com.gnoemes.shikimoriapp.data.repository.app.converter.GenreResponseConverter
import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter
import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverter
import com.gnoemes.shikimoriapp.entity.manga.data.MangaDetailsResponse
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse
import javax.inject.Inject

class MangaDetailsResponseConverterImpl @Inject constructor(
        private val imageResponseConverter: ImageResponseConverter,
        private val genreResponseConverter: GenreResponseConverter,
        private val animeRateResponseConverter: AnimeRateResponseConverter,
        private val rolesResponseConverter: RolesResponseConverter
) : MangaDetailsResponseConverter {

    override fun convertResponse(response: MangaDetailsResponse, roles: List<RolesResponse>): MangaDetails =
            MangaDetails(
                    response.id,
                    response.name,
                    response.nameRu,
                    imageResponseConverter.convert(response.image),
                    response.url,
                    response.type,
                    response.status,
                    response.volumes,
                    response.chapters,
                    response.dateAired,
                    response.dateReleased,
                    response.score,
                    response.description,
                    response.descriptionHtml,
                    response.favoured,
                    response.topicId,
                    genreResponseConverter.convertGenres(response.genres),
                    rolesResponseConverter.convertCharacters(roles),
                    animeRateResponseConverter.convertUserRateResponse(response.userRate)
            )
}