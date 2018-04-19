package com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider;

import android.support.annotation.ColorRes;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

public interface CalendarAnimeResourceProvider {

    String getEpisodeStringFormat();

    String getEpisodeInString();

    @ColorRes
    int getColorByAnimeType(AnimeType type);
}
