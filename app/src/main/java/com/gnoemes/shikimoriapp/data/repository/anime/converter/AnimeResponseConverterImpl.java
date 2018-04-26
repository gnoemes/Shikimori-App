package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

import javax.inject.Inject;

public class AnimeResponseConverterImpl implements AnimeResponseConverter {

    @Inject
    AnimeResponseConverterImpl() {
    }

    @Override
    public Anime convertFrom(AnimeResponse animeResponse) {
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
    public AnimeImage convertAnimeImage(AnimeImageResponse image) {
        return new AnimeImage(image.getImageOriginalUrl(),
                image.getImagePreviewUrl(),
                image.getImageX96Url(),
                image.getImageX48Url());
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
            throw new IllegalArgumentException(type + " is not valid type");
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
            throw new IllegalArgumentException(status + " is not valid status");
        }
    }
}
