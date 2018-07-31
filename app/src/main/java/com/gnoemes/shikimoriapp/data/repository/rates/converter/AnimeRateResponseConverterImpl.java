package com.gnoemes.shikimoriapp.data.repository.rates.converter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.data.AnimeRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateCreateRequest;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateUpdateRequest;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeRateResponseConverterImpl implements AnimeRateResponseConverter {

    private AnimeResponseConverter animeResponseConverter;
    private UserBriefResponseConverter userBriefResponseConverter;

    @Inject
    public AnimeRateResponseConverterImpl(AnimeResponseConverter animeResponseConverter,
                                          UserBriefResponseConverter userBriefResponseConverter) {
        this.animeResponseConverter = animeResponseConverter;
        this.userBriefResponseConverter = userBriefResponseConverter;
    }

    @Override
    public List<AnimeRate> apply(List<AnimeRateResponse> responses) {
        List<AnimeRate> animeRates = new ArrayList<>();

        if (responses == null) {
            return animeRates;
        }

        for (AnimeRateResponse response : responses) {
            animeRates.add(convertResponse(response));
        }

        return animeRates;
    }

    @Override
    public AnimeRate convertResponse(@Nullable AnimeRateResponse response) {
        if (response == null) {
            return null;
        }

        return new AnimeRate(response.getId(),
                response.getScore(),
                convertStatus(response.getStatus()),
                response.getText(),
                response.getEpisodes(),
                response.getChapters(),
                response.getVolumes(),
                response.getRewatches(),
                userBriefResponseConverter.apply(response.getUserBriefResponse()),
                animeResponseConverter.convertFrom(response.getAnimeResponse()));
    }

    @Override
    public UserRate convertLocalSyncRate(UserRate rate, Integer count) {
        return new UserRate(rate.getId(),
                rate.getStatus(),
                rate.getScore(),
                rate.getText(),
                TextUtils.isEmpty(rate.getEpisodes()) ? String.valueOf(count) : rate.getEpisodes(),
                rate.getRewatches());
    }

    @Override
    public UserRate convertUserRateResponse(UserRateResponse response) {
        if (response == null) {
            return null;
        }

        return new UserRate(response.getId(),
                convertStatus(response.getStatus()),
                response.getScore(),
                response.getText(),
                response.getEpisodes(),
                response.getRewatches());
    }

    private RateStatus convertStatus(String status) {
        for (RateStatus rateStatus : RateStatus.values()) {
            if (rateStatus.isEqualStatus(status)) {
                return rateStatus;
            }
        }
        return null;
    }

    @Override
    public UserRateCreateRequest convertCreateRequest(long targetId, Type type, UserRate rate, long userId) {
        return new UserRateCreateRequest(new UserRateCreateRequest(
                rate.getRewatches(),
                rate.getEpisodes(),
                rate.getScore(),
                rate.getStatus().getStatus(),
                String.valueOf(targetId),
                type.getType(),
                rate.getText(),
                String.valueOf(userId)
        ));
    }

    @Override
    public UserRateUpdateRequest convertUpdateRequest(UserRate rate) {
        return new UserRateUpdateRequest(new UserRateUpdateRequest(
                rate.getRewatches(),
                rate.getEpisodes(),
                rate.getScore(),
                rate.getStatus().getStatus(),
                rate.getText()
        ));
    }
}
