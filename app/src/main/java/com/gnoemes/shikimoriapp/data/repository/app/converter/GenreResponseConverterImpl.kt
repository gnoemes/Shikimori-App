package com.gnoemes.shikimoriapp.data.repository.app.converter

import com.gnoemes.shikimoriapp.entity.anime.data.GenreResponse
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import javax.inject.Inject

class GenreResponseConverterImpl @Inject constructor() : GenreResponseConverter {

    override fun convertGenres(responses: List<GenreResponse>): List<Genre> {
        val genres = mutableListOf<Genre>()

        responses.forEach { response ->
            val genre = Genre.values().firstOrNull { it.equalsName(convertGenreName(response.name)) }
            if (genre != null) genres.add(genre)
        }

        return genres
    }

    private fun convertGenreName(name: String): String {
        val builder = StringBuilder()
        name.toCharArray().forEach {
            if (Character.isWhitespace(it) || it == '-') builder.append('_') else builder.append(it)
        }
        return builder.toString().toLowerCase()
    }
}