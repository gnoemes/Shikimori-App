package com.gnoemes.shikimoriapp.data.repository.app.converter

import com.gnoemes.shikimoriapp.entity.anime.data.GenreResponse
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre

interface GenreResponseConverter {

    fun convertGenres(responses: List<GenreResponse>): List<Genre>
}