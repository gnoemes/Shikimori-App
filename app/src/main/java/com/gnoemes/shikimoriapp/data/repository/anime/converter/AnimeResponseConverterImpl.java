package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.utils.PrefUtils;

import javax.inject.Inject;

public class AnimeResponseConverterImpl implements AnimeResponseConverter {

    private Context context;
    private ImageResponseConverter imageResponseConverter;

    @Inject
    public AnimeResponseConverterImpl(Context context,
                                      ImageResponseConverter imageResponseConverter) {
        this.context = context;
        this.imageResponseConverter = imageResponseConverter;
    }

    @Override
    public Anime convertFrom(@Nullable AnimeResponse animeResponse) {
        if (animeResponse == null) {
            return null;
        }

        boolean isRomandziNaming = PrefUtils.isRomandziNaming(context);
        String name = isRomandziNaming ? animeResponse.getName() : animeResponse.getRussianName();
        String secondName = isRomandziNaming ? animeResponse.getRussianName() : animeResponse.getName();

        return new Anime(animeResponse.getId(),
                name,
                secondName,
                imageResponseConverter.convert(animeResponse.getImage()),
                animeResponse.getUrl(),
                convertAnimeType(animeResponse.getType()),
                convertAnimeStatus(animeResponse.getStatus()),
                animeResponse.getEpisodes(),
                animeResponse.getEpisodesAired(),
                animeResponse.getAiredDate(),
                animeResponse.getReleasedDate());
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
