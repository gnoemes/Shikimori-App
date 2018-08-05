package com.gnoemes.shikimoriapp.data.repository.rates;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.network.UserApi;
import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRatesRepositoryImpl implements UserRatesRepository {

    private UserApi userApi;
    private HistoryDbSource historyDbSource;
    private AnimeRateResponseConverter animeRateResponseConverter;

    @Inject
    public UserRatesRepositoryImpl(@NonNull UserApi userApi,
                                   @NonNull HistoryDbSource historyDbSource,
                                   @NonNull AnimeRateResponseConverter animeRateResponseConverter) {
        this.userApi = userApi;
        this.historyDbSource = historyDbSource;
        this.animeRateResponseConverter = animeRateResponseConverter;
    }

    @Override
    public Single<List<AnimeRate>> getUserAnimeRates(long id, int page, int limit) {
        return userApi.getUserAnimeRates(id, page, limit, null)
                .map(animeRateResponseConverter);
    }

    @Override
    public Single<List<AnimeRate>> getUserAnimeRates(long id, int page, int limit, RateStatus status) {
        return userApi.getUserAnimeRates(id, page, limit, status.getStatus())
                .map(animeRateResponseConverter)
                .doOnSuccess(rates -> {
                    if (page > 1) {
                        rates.remove(0);
                    }
                })
                ;
    }

    @Override
    public Single<List<AnimeRate>> getUserAnimeRates(UserStatus userStatus, long id, int page, int limit, RateStatus status) {
        switch (userStatus) {
            case AUTHORIZED:
                return getUserAnimeRates(id, page, limit, status);
            case GUEST:
                return Single.just(Collections.emptyList());
        }
        throw new IllegalStateException();
    }

    @Override
    public Completable deleteRate(long id) {
        return userApi.deleteRate(id);
    }

    @Override
    public Completable createRate(long id, Type type, UserRate userRate, long userId) {
        return historyDbSource.getWatchedEpisodesCount(id)
                .map(count -> animeRateResponseConverter.convertLocalSyncRate(userRate, count))
                .map(rate -> animeRateResponseConverter.convertCreateRequest(id, type, rate, userId))
                .flatMapCompletable(request -> userApi.createRate(request));
    }

    @Override
    public Completable updateRate(UserRate rate) {
        return userApi.updateRate(rate.getId(), animeRateResponseConverter.convertUpdateRequest(rate));
    }

    @Override
    public Completable onEpisodeWatched(long rateId) {
        return userApi.incrementEpisode(rateId);
    }
}
