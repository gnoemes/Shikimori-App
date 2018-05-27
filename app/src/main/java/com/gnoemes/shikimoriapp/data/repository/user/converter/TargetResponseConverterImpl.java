package com.gnoemes.shikimoriapp.data.repository.user.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.user.domain.Target;
import com.gnoemes.shikimoriapp.entity.user.domain.TargetType;

import javax.inject.Inject;

public class TargetResponseConverterImpl implements TargetResponseConverter {

    @Inject
    public TargetResponseConverterImpl() {
    }

    @Override
    public Target convertFrom(@Nullable AnimeResponse animeResponse) {
        if (animeResponse == null) {
            return null;
        }

        return new Target(animeResponse.getId(),
                animeResponse.getName(),
                animeResponse.getRussianName(),
                convertAnimeImage(animeResponse.getImage()),
                animeResponse.getUrl(),
                convertAnimeType(animeResponse.getType()),
                convertAnimeStatus(animeResponse.getStatus()),
                animeResponse.getEpisodes(),
                animeResponse.getEpisodesAired(),
                animeResponse.getAiredDate(),
                animeResponse.getReleasedDate());
    }

    private AnimeImage convertAnimeImage(AnimeImageResponse image) {
        return new AnimeImage(
                buildUrl(image.getImageOriginalUrl()),
                buildUrl(image.getImagePreviewUrl()),
                buildUrl(image.getImageX96Url()),
                buildUrl(image.getImageX48Url()));
    }

    private String buildUrl(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }

    private TargetType convertAnimeType(String type) {
        for (TargetType targetType : TargetType.values()) {
            if (targetType.equalsType(type)) {
                return targetType;
            }
        }
        return null;
    }

    private AnimeStatus convertAnimeStatus(String status) {
        if (AnimeStatus.ANONS.equalsStatus(status)) {
            return AnimeStatus.ANONS;
        } else if (AnimeStatus.ONGOING.equalsStatus(status)) {
            return AnimeStatus.ONGOING;
        } else if (AnimeStatus.RELEASED.equalsStatus(status)) {
            return AnimeStatus.RELEASED;
        } else {
            throw new IllegalArgumentException(status + " is not valid status");
        }
    }
}
