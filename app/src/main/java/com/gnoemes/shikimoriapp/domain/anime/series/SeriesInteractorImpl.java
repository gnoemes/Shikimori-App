package com.gnoemes.shikimoriapp.domain.anime.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
import com.gnoemes.shikimoriapp.data.repository.series.SeriesRepository;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
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
    public Single<Series> getEpisodes(long animeId) {
        return repository.getAnimeSeries(animeId)
                .compose((SingleErrorHandler<Series>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Completable setEpisodeWatched(long animeId, long episodeId) {
        return repository.isEpisodeWatched(episodeId)
                .flatMapCompletable(isWatched -> {
                    if (!isWatched) {
                        return repository.setEpisodeWatched(animeId, episodeId);
                    } else {
                        return Completable.complete();
                    }
                })
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable setEpisodeWatched(long animeId, long episodeId, long rateId) {
        return repository.isEpisodeWatched(episodeId)
                .flatMapCompletable(isWatched -> {
                    if (!isWatched) {
                        return repository.setEpisodeWatched(animeId, episodeId)
                                .andThen(ratesRepository.onEpisodeWatched(rateId));
                    } else {
                        return Completable.complete();
                    }
                })
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Single<List<Translation>> getEpisodeTranslations(TranslationType type, long animeId, int episodeId) {
        return repository.getTranslations(type, animeId, episodeId)
                .compose((SingleErrorHandler<List<Translation>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }


    @Override
    public Completable clearHistory(long animeId) {
        return repository.clearHistory(animeId)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
