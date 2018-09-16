package com.gnoemes.shikimoriapp.domain.anime.series;

import com.gnoemes.shikimoriapp.data.repository.alternative.AlternativeRepository;
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AlternativeSeriesInteractorImpl implements AlternativeSeriesInteractor {

    private AlternativeRepository repository;
    private UserRatesRepository ratesRepository;
    private UserSettingsRepository settingsRepository;
    private SingleErrorHandler singleErrorHandler;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public AlternativeSeriesInteractorImpl(AlternativeRepository repository,
                                           UserRatesRepository ratesRepository,
                                           UserSettingsRepository settingsRepository,
                                           SingleErrorHandler singleErrorHandler,
                                           CompletableErrorHandler completableErrorHandler,
                                           RxUtils rxUtils) {
        this.repository = repository;
        this.ratesRepository = ratesRepository;
        this.settingsRepository = settingsRepository;
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
    public Completable setEpisodeWatched(long animeId, long episodeId, long rateId) {
        return repository.isEpisodeWatched(animeId, episodeId)
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
    public Completable setEpisodeWatched(long animeId, long episodeId) {
        return repository.isEpisodeWatched(animeId, episodeId)
                .flatMapCompletable(isWatched -> {
                    if (isWatched) {
                        return repository.setEpisodeWatched(animeId, episodeId)
                                .toSingleDefault(settingsRepository.getUser())
                                .flatMapCompletable(user -> ratesRepository.createRate(animeId, Type.ANIME, UserRate.getStartWatching(), user.getId()));
                    } else {
                        return Completable.complete();
                    }
                })
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Single<List<AlternativeTranslation>> getEpisodeTranslations(AlternativeTranslationType type, long animeId, long episodeId) {
        return repository.getTranslations(type, animeId, episodeId)
                .compose((SingleErrorHandler<List<AlternativeTranslation>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Completable clearHistory(long animeId) {
        return repository.clearHistory(animeId)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
