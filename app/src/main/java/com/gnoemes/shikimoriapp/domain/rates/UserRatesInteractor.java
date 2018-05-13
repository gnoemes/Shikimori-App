package com.gnoemes.shikimoriapp.domain.rates;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRatesInteractor {

    Single<List<AnimeRate>> getUserRates(long id, int page, int limit, RateStatus status);

    Single<List<AnimeRate>> getUserRates(long id, int page, int limit);

    Completable deleteRate(long id);

    Completable createRate(long targetId, Type anime, UserRate rate, long userId);

    Completable updateRate(UserRate rate);
}
