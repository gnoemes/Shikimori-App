package com.gnoemes.shikimoriapp.data.repository.manga.converter

import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.utils.appendHostIfNeed
import javax.inject.Inject

class MangaResponseConverterImpl @Inject constructor(
        val imageResponseConverter: ImageResponseConverter
) : MangaResponseConverter {

    override fun apply(responses: MutableList<MangaResponse>): List<Manga?> {
        return responses.map { convertResponse(it) }
    }

    override fun convertResponse(response: MangaResponse?): Manga? {

        if (response == null) {
            return null
        }

        return Manga(response.id,
                response.name,
                response.nameRu,
                imageResponseConverter.convert(response.image),
                response.url.appendHostIfNeed(),
                response.type,
                response.status,
                response.volumes,
                response.chapters,
                response.dateAired,
                response.dateReleased
        )
    }
}