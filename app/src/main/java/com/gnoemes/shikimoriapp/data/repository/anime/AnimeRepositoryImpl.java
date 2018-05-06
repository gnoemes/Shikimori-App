package com.gnoemes.shikimoriapp.data.repository.anime;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeLinkResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AnimeRepositoryImpl implements AnimeRepository {

    private AnimesApi animesApi;
    private AnimeDetailsResponseConverter responseConverter;
    private AnimeListResponseConverter listResponseConverter;
    private AnimeLinkResponseConverter linkReponseConverter;

    @Inject
    public AnimeRepositoryImpl(AnimesApi animesApi,
                               AnimeDetailsResponseConverter responseConverter,
                               AnimeLinkResponseConverter linkReponseConverter,
                               AnimeListResponseConverter listResponseConverter) {
        this.animesApi = animesApi;
        this.responseConverter = responseConverter;
        this.linkReponseConverter = linkReponseConverter;
        this.listResponseConverter = listResponseConverter;
    }

    @Override
    public Single<AnimeDetails> getAnimeDetails(long animeId) {
        return animesApi.getAnimeDetails(animeId)
                .map(responseConverter);
    }

    @Override
    public Single<List<AnimeLink>> getAnimeLinks(long animeId) {
        return animesApi.getAnimeLinks(animeId)
                .map(linkReponseConverter);
    }

    @Override
    public Single<List<Anime>> getSimilarAnimes(long animeId) {
        return animesApi.getSimilarAnimes(animeId)
                .map(listResponseConverter);
    }
}
