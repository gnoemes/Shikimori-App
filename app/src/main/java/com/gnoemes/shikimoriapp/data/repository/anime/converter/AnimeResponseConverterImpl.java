package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.DefaultImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.utils.Utils;

import javax.inject.Inject;

public class AnimeResponseConverterImpl implements AnimeResponseConverter {

    @Inject
    AnimeResponseConverterImpl() {
    }

    @Override
    public Anime convertFrom(@Nullable AnimeResponse animeResponse) {
        if (animeResponse == null) {
            return null;
        }

        return new Anime(animeResponse.getId(),
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

    @Override
    public AnimeImage convertAnimeImage(DefaultImageResponse image) {
        return new AnimeImage(
                Utils.appendHostIfNeed(image.getImageOriginalUrl()),
                Utils.appendHostIfNeed(image.getImagePreviewUrl()),
                Utils.appendHostIfNeed(image.getImageX96Url()),
                Utils.appendHostIfNeed(image.getImageX48Url()));
    }

    @Override
    public AnimeType convertAnimeType(String type) {

        if (AnimeType.TV.equalsType(type)) {
            return AnimeType.TV;
        } else if (AnimeType.MOVIE.equalsType(type)) {
            return AnimeType.MOVIE;
        } else if (AnimeType.OVA.equalsType(type)) {
            return AnimeType.OVA;
        } else if (AnimeType.ONA.equalsType(type)) {
            return AnimeType.ONA;
        } else if (AnimeType.SPECIAL.equalsType(type)) {
            return AnimeType.SPECIAL;
        } else if (AnimeType.MUSIC.equalsType(type)) {
            return AnimeType.MUSIC;
        } else {
            return AnimeType.NONE;
        }
    }

    @Override
    public AnimeStatus convertAnimeStatus(String status) {

        if (AnimeStatus.ANONS.equalsStatus(status)) {
            return AnimeStatus.ANONS;
        } else if (AnimeStatus.ONGOING.equalsStatus(status)) {
            return AnimeStatus.ONGOING;
        } else if (AnimeStatus.RELEASED.equalsStatus(status)) {
            return AnimeStatus.RELEASED;
        } else {
            return AnimeStatus.NONE;
        }
    }
}
