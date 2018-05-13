package com.gnoemes.shikimoriapp.data.repository.rates;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRatesRepository {

    Single<List<AnimeRate>> getUserAnimeRates(long id, int page, int limit);

    Single<List<AnimeRate>> getUserAnimeRates(long id, int page, int limit, RateStatus status);

    Single<List<AnimeRate>> getUserAnimeRates(UserStatus userStatus, long id, int page, int limit, RateStatus status);

    Completable deleteRate(long id);

    Completable createRate(long id, Type type, UserRate rate, long userId);

    Completable updateRate(UserRate rate);

    Completable onEpisodeWatched(long rateId);
}
