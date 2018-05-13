package com.gnoemes.shikimoriapp.domain.rates;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRatesInteractorImpl implements UserRatesInteractor {

    private UserRatesRepository repository;
    private UserSettingsRepository settingsRepository;
    private SingleErrorHandler singleErrorHandler;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public UserRatesInteractorImpl(@NonNull UserRatesRepository repository,
                                   @NonNull UserSettingsRepository settingsRepository,
                                   @NonNull SingleErrorHandler singleErrorHandler,
                                   @NonNull CompletableErrorHandler completableErrorHandler,
                                   @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.settingsRepository = settingsRepository;
        this.singleErrorHandler = singleErrorHandler;
        this.completableErrorHandler = completableErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<AnimeRate>> getUserRates(long id, int page, int limit, RateStatus status) {
        return settingsRepository.getUserSettings()
                .firstOrError()
                .flatMap(settings -> repository.getUserAnimeRates(settings.getStatus(), id, page, limit, status))
                .compose((SingleErrorHandler<List<AnimeRate>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<AnimeRate>> getUserRates(long id, int page, int limit) {
        return repository.getUserAnimeRates(id, page, limit)
                .compose((SingleErrorHandler<List<AnimeRate>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Completable deleteRate(long id) {
        return repository.deleteRate(id)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable createRate(long targetId, Type type, UserRate rate, long userId) {
        return repository.createRate(targetId, type, rate, userId)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable updateRate(UserRate rate) {
        return repository.updateRate(rate)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
