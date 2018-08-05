package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.DefaultImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

public interface AnimeResponseConverter {

    /**
     * Convert data model to domain model
     *
     * @param animeResponse data model
     * @return Anime domain model
     */
    Anime convertFrom(@Nullable AnimeResponse animeResponse);

    AnimeImage convertAnimeImage(DefaultImageResponse image);

    AnimeType convertAnimeType(String type);

    AnimeStatus convertAnimeStatus(String status);

}
