package com.gnoemes.shikimoriapp.data.repository.rates.converter;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.data.AnimeRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateCreateOrUpdateRequest;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;

import java.util.List;

import io.reactivex.functions.Function;

public interface AnimeRateResponseConverter extends Function<List<AnimeRateResponse>, List<AnimeRate>> {
    AnimeRate convertResponse(AnimeRateResponse response);

    UserRate convertUserRateResponse(UserRateResponse response);

    UserRateCreateOrUpdateRequest convertCreateRequest(long targetId, Type type, UserRate rate, long userId);

    UserRateCreateOrUpdateRequest convertUpdateRequest(UserRate rate);

    UserRate convertLocalSyncRate(UserRate rate, Integer count);
}
