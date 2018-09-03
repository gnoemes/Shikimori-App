package com.gnoemes.shikimoriapp.data.repository.app.converter

import com.gnoemes.shikimoriapp.entity.anime.data.GenreResponse
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import java.util.*
import javax.inject.Inject

class GenreResponseConverterImpl @Inject constructor() : GenreResponseConverter {

    //TODO copy-pasted from java, refactor
    override fun convertGenres(responses: List<GenreResponse>): List<Genre> {
        val genres = ArrayList<Genre>()

        for (genre in responses) {
            for (animeGenre in Genre.values()) {
                val name = convertGenreName(genre.name)
                if (animeGenre.equalsName(name)) {
                    genres.add(animeGenre)
                }
            }
        }
        return genres
    }

    private fun convertGenreName(name: String): String {
        val builder = StringBuilder()
        for (c in name.toCharArray()) {
            if (Character.isWhitespace(c)) {
                builder.append('_')
            } else {
                builder.append(c)
            }
        }
        return builder.toString().toLowerCase()
    }
}