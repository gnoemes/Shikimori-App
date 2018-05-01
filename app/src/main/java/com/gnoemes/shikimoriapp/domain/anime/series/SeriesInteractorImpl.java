package com.gnoemes.shikimoriapp.domain.anime.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.anime.series.SeriesRepository;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SeriesInteractorImpl implements SeriesInteractor {

    private SeriesRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public SeriesInteractorImpl(@NonNull SeriesRepository repository,
                                @NonNull SingleErrorHandler singleErrorHandler,
                                @NonNull CompletableErrorHandler completableErrorHandler,
                                @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.completableErrorHandler = completableErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Episode>> getEpisodes(long animeId) {
        return repository.getAnimeEpisodes(animeId)
                .compose((SingleErrorHandler<List<Episode>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Completable setEpisodeWatched(long episodeId) {
        return null;
    }

    @Override
    public Single<List<Translation>> getEpisodeTranslations(TranslationType type, long episodeId) {
        return repository.getTranslations(type, episodeId)
                .compose((SingleErrorHandler<List<Translation>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<Translation> getAutoTranslation(TranslationType type, long episodeId) {
        return repository.getTranslations(type, episodeId)
                .flatMap(translations -> Observable
                        .fromIterable(translations)
                        .firstOrError())
                .compose((SingleErrorHandler<Translation>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }


}
