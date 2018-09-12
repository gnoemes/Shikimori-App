package com.gnoemes.shikimoriapp.data.repository.rates.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.data.AnimeRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateCreateOrUpdateRequest;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
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
                response.getStatus(),
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
        return new UserRate(
                rate.getId(),
                rate.getUserId(),
                rate.getTargetId(),
                rate.getTargetType(),
                rate.getScore(),
                rate.getStatus(),
                rate.getRewatches(),
                rate.getEpisodes() == null ? count : rate.getEpisodes(),
                rate.getVolumes(),
                rate.getChapters(),
                rate.getText(),
                rate.getTextHtml(),
                rate.getDateCreated(),
                rate.getDateUpdated()
        );
    }

    @Override
    public UserRate convertUserRateResponse(UserRateResponse response) {
        if (response == null) {
            return null;
        }

        return new UserRate(response.getId(),
                response.getUserId(),
                response.getTargetId(),
                response.getTargetType(),
                response.getScore(),
                response.getStatus(),
                response.getRewatches(),
                response.getEpisodes(),
                response.getVolumes(),
                response.getChapters(),
                response.getText(),
                response.getTextHtml(),
                response.getDateCreated(),
                response.getDateUpdated()
        );
    }

    @Override
    public UserRateCreateOrUpdateRequest convertCreateRequest(long targetId, Type type, UserRate rate, long userId) {
        return new UserRateCreateOrUpdateRequest(new UserRateResponse(
                rate.getId(),
                userId,
                targetId,
                type,
                rate.getScore(),
                rate.getStatus(),
                rate.getRewatches(),
                rate.getEpisodes(),
                rate.getVolumes(),
                rate.getChapters(),
                rate.getText(),
                rate.getTextHtml(),
                rate.getDateCreated(),
                rate.getDateUpdated()
        ));
    }

    @Override
    public UserRateCreateOrUpdateRequest convertUpdateRequest(UserRate rate) {
        return new UserRateCreateOrUpdateRequest(new UserRateResponse(
                rate.getId(),
                rate.getUserId(),
                rate.getTargetId(),
                rate.getTargetType(),
                rate.getScore(),
                rate.getStatus(),
                rate.getRewatches(),
                rate.getEpisodes(),
                rate.getVolumes(),
                rate.getChapters(),
                rate.getText(),
                rate.getTextHtml(),
                rate.getDateCreated(),
                rate.getDateUpdated()
        ));
    }
}
