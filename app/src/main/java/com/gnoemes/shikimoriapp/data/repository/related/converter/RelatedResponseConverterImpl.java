package com.gnoemes.shikimoriapp.data.repository.related.converter;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.related.data.RelatedResponse;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RelatedResponseConverterImpl implements RelatedResponseConverter {

    private AnimeResponseConverter animeConverter;
    private MangaResponseConverter mangaConverter;

    @Inject
    public RelatedResponseConverterImpl(AnimeResponseConverter animeConverter,
                                        MangaResponseConverter mangaConverter) {
        this.animeConverter = animeConverter;
        this.mangaConverter = mangaConverter;
    }

    @Override
    public List<Related> apply(List<RelatedResponse> relatedResponses) {
        List<Related> items = new ArrayList<>();

        for (RelatedResponse response : relatedResponses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    private Related convertResponse(RelatedResponse response) {
        return new Related(response.getRelation(),
                response.getRelationRussian(),
                response.getAnimeResponse() != null ? Type.ANIME : Type.MANGA,
                animeConverter.convertFrom(response.getAnimeResponse()),
                mangaConverter.convertResponse(response.getMangaResponse()));
    }
}
