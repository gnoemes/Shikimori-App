package com.gnoemes.shikimoriapp.data.repository.related;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.repository.related.converter.RelatedResponseConverter;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RelatedRepositoryImpl implements RelatedRepository {

    private AnimesApi animesApi;
    private RelatedResponseConverter responseConverter;

    @Inject
    public RelatedRepositoryImpl(AnimesApi animesApi,
                                 RelatedResponseConverter responseConverter) {
        this.animesApi = animesApi;
        this.responseConverter = responseConverter;
    }

    @Override
    public Single<List<Related>> getRelatedAnime(long animeId) {
        return animesApi.getRelatedItems(animeId)
                .map(responseConverter);
    }

    @Override
    public Single<List<Related>> getRelatedManga(long mangaId) {
        //TODO add
        return null;
    }
}
