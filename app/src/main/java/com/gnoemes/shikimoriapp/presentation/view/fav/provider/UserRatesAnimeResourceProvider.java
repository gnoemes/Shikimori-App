package com.gnoemes.shikimoriapp.presentation.view.fav.provider;

import com.gnoemes.shikimoriapp.presentation.view.main.provider.AnimeResourceProvider;

public interface UserRatesAnimeResourceProvider extends AnimeResourceProvider {
    String getEpisodeRateStringFormat();

    String getWachingString();

    String getPlannedString();

    String getReWatchingString();

    String getCompletedString();

    String getOnHoldString();

    String getDroppedString();

    String getFavString();
}
