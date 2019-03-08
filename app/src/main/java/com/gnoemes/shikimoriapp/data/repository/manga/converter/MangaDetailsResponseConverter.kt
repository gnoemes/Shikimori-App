package com.gnoemes.shikimoriapp.data.repository.manga.converter

import com.gnoemes.shikimoriapp.entity.manga.data.MangaDetailsResponse
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse

interface MangaDetailsResponseConverter {

    fun convertResponse(response: MangaDetailsResponse, roles: List<RolesResponse>): MangaDetails
}