package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

public interface AnimeResponseConverter {

    /**
     * Convert data model to domain model
     *
     * @param animeResponse data model
     * @return Anime domain model
     */
    Anime convertFrom(@Nullable AnimeResponse animeResponse);
}
