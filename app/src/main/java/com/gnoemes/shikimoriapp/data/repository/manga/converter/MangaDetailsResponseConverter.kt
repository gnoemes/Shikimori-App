package com.gnoemes.shikimoriapp.data.repository.manga.converter

import com.gnoemes.shikimoriapp.entity.manga.data.MangaDetailsResponse
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails

interface MangaDetailsResponseConverter {

    fun convertResponse(response: MangaDetailsResponse): MangaDetails
}