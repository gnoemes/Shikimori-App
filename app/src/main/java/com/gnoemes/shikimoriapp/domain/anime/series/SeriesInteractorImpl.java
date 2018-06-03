package com.gnoemes.shikimoriapp.domain.anime.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.anime.series.SeriesRepository;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
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
    private UserRatesRepository ratesRepository;
    private SingleErrorHandler singleErrorHandler;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public SeriesInteractorImpl(@NonNull SeriesRepository repository,
                                @NonNull UserRatesRepository ratesRepository,
                                @NonNull SingleErrorHandler singleErrorHandler,
                                @NonNull CompletableErrorHandler completableErrorHandler,
                                @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.ratesRepository = ratesRepository;
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
    public Completable setEpisodeWatched(long animeId, long episodeId) {
        return repository.setEpisodeWatched(animeId, episodeId)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable setEpisodeWatched(long animeId, long episodeId, long rateId) {
        return repository.setEpisodeWatched(animeId, episodeId)
                .andThen(ratesRepository.onEpisodeWatched(rateId))
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
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

    @Override
    public Completable clearHistory(long animeId) {
        return repository.clearHistory(animeId)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
