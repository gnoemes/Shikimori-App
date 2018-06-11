package com.gnoemes.shikimoriapp.presentation.view.main.provider;

import android.support.annotation.ColorRes;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

public interface AnimeResourceProvider {

    String getEpisodeStringFormat();

    @ColorRes
    int getColorByAnimeType(AnimeType type);

    String getLocalizedType(AnimeType type);
}
