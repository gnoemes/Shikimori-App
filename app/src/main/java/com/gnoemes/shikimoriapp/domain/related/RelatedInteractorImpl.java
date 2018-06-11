package com.gnoemes.shikimoriapp.domain.related;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.related.RelatedRepository;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RelatedInteractorImpl implements RelatedInteractor {

    private RelatedRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public RelatedInteractorImpl(@NonNull RelatedRepository repository,
                                 @NonNull SingleErrorHandler singleErrorHandler,
                                 @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Related>> getRelatedAnime(long animeId) {
        return repository.getRelatedAnime(animeId)
                .compose((SingleErrorHandler<List<Related>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Related>> getRelatedManga(long mangaId) {
        return repository.getRelatedManga(mangaId)
                .compose((SingleErrorHandler<List<Related>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
