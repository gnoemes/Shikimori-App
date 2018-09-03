package com.gnoemes.shikimoriapp.data.repository.user.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Status;
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
                animeResponse.getRussianName(),
                imageResponseConverter.convert(animeResponse.getImage()),
                animeResponse.getUrl(),
                convertAnimeType(animeResponse.getType()),
                convertAnimeStatus(animeResponse.getStatus()),
                animeResponse.getEpisodes(),
                animeResponse.getEpisodesAired(),
                animeResponse.getAiredDate(),
                animeResponse.getReleasedDate());
    }

    private TargetType convertAnimeType(String type) {
        for (TargetType targetType : TargetType.values()) {
            if (targetType.equalsType(type)) {
                return targetType;
            }
        }
        return null;
    }

    private Status convertAnimeStatus(String status) {
        if (Status.ANONS.equalsStatus(status)) {
            return Status.ANONS;
        } else if (Status.ONGOING.equalsStatus(status)) {
            return Status.ONGOING;
        } else if (Status.RELEASED.equalsStatus(status)) {
            return Status.RELEASED;
        } else {
            throw new IllegalArgumentException(status + " is not valid status");
        }
    }
}
