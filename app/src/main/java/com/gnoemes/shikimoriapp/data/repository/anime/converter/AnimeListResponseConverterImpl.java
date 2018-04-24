package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeListResponseConverterImpl implements AnimeListResponseConverter {

    private AnimeResponseConverter responseConverter;

    @Inject
    public AnimeListResponseConverterImpl(AnimeResponseConverter responseConverter) {
        this.responseConverter = responseConverter;
    }

    @Override
    public List<Anime> apply(List<AnimeResponse> animeResponses) {
        List<Anime> animeList = new ArrayList<>();
        for (AnimeResponse response : animeResponses) {
            animeList.add(responseConverter.convertFrom(response));
        }
        return animeList;
    }
}
