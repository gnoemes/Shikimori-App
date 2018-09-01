package com.gnoemes.shikimoriapp.data.repository.user.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.data.repository.common.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.DefaultImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.user.domain.Target;
import com.gnoemes.shikimoriapp.entity.user.domain.TargetType;

import javax.inject.Inject;

public class TargetResponseConverterImpl implements TargetResponseConverter {

    private ImageResponseConverter imageResponseConverter;

    @Inject
    public TargetResponseConverterImpl(ImageResponseConverter imageResponseConverter) {
        this.imageResponseConverter = imageResponseConverter;
    }

    @Override
    public Target convertFrom(@Nullable AnimeResponse animeResponse) {
        if (animeResponse == null) {
            return null;
        }

        return new Target(animeResponse.getId(),
                animeResponse.getName(),
                animeResponse.getNameRu(),
                imageResponseConverter.convert(animeResponse.getImage()),
                animeResponse.getUrl(),
                convertAnimeType(animeResponse.getType()),
                animeResponse.getStatus(),
                animeResponse.getEpisodes(),
                animeResponse.getEpisodesAired(),
                animeResponse.getDateAired(),
                animeResponse.getDateReleased());
    }

    private AnimeImage convertAnimeImage(DefaultImageResponse image) {
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

    private TargetType convertAnimeType(AnimeType type) {
        for (TargetType targetType : TargetType.values()) {
            if (targetType.equalsType(type.toString())) {
                return targetType;
            }
        }
        return null;
    }

    private AnimeStatus convertAnimeStatus(String status) {
        if (AnimeStatus.ANONS.name().equalsIgnoreCase(status)) {
            return AnimeStatus.ANONS;
        } else if (AnimeStatus.ONGOING.name().equalsIgnoreCase(status)) {
            return AnimeStatus.ONGOING;
        } else if (AnimeStatus.RELEASED.name().equalsIgnoreCase(status)) {
            return AnimeStatus.RELEASED;
        } else {
            throw new IllegalArgumentException(status + " is not valid status");
        }
    }
}
