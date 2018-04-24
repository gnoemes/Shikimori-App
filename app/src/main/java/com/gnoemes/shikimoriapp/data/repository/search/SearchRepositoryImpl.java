package com.gnoemes.shikimoriapp.data.repository.search;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

public class SearchRepositoryImpl implements SearchRepository {

    private AnimesApi animesApi;
    private AnimeListResponseConverter responseConverter;

    @Inject
    public SearchRepositoryImpl(AnimesApi animesApi,
                                AnimeListResponseConverter responseConverter) {
        this.animesApi = animesApi;
        this.responseConverter = responseConverter;
    }

    @Override
    public Single<List<Anime>> getAnimeList(Map<String, String> queryMap) {
        return animesApi.getAnimeList(queryMap)
                .map(responseConverter);
    }
}
