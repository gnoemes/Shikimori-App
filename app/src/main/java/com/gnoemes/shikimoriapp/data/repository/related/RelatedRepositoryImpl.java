package com.gnoemes.shikimoriapp.data.repository.related;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.network.MangaApi;
import com.gnoemes.shikimoriapp.data.network.RanobeApi;
import com.gnoemes.shikimoriapp.data.repository.related.converter.RelatedResponseConverter;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RelatedRepositoryImpl implements RelatedRepository {

    private AnimesApi animesApi;
    private MangaApi mangaApi;
    private RanobeApi ranobeApi;
    private RelatedResponseConverter responseConverter;

    @Inject
    public RelatedRepositoryImpl(AnimesApi animesApi,
                                 MangaApi mangaApi,
                                 RanobeApi ranobeApi,
                                 RelatedResponseConverter responseConverter) {
        this.animesApi = animesApi;
        this.mangaApi = mangaApi;
        this.ranobeApi = ranobeApi;
        this.responseConverter = responseConverter;
    }

    @Override
    public Single<List<Related>> getRelatedAnime(long animeId) {
        return animesApi.getRelatedItems(animeId)
                .map(responseConverter);
    }

    @Override
    public Single<List<Related>> getRelatedManga(long mangaId) {
        return mangaApi.getRelated(mangaId)
                .map(responseConverter);
    }

    @Override
    public Single<List<Related>> getRelatedRanobe(long mangaId) {
        return ranobeApi.getRelated(mangaId)
                .map(responseConverter);
    }
}
