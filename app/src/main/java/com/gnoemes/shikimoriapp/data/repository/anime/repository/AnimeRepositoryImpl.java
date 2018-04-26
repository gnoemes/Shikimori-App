package com.gnoemes.shikimoriapp.data.repository.anime.repository;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;

import javax.inject.Inject;

import io.reactivex.Single;

public class AnimeRepositoryImpl implements AnimeRepository {

    private AnimesApi animesApi;
    private AnimeDetailsResponseConverter responseConverter;

    @Inject
    public AnimeRepositoryImpl(AnimesApi animesApi, AnimeDetailsResponseConverter responseConverter) {
        this.animesApi = animesApi;
        this.responseConverter = responseConverter;
    }

    @Override
    public Single<AnimeDetails> getAnimeDetails(long animeId) {
        return animesApi.getAnimeDetails(animeId)
                .map(responseConverter);
    }
}
