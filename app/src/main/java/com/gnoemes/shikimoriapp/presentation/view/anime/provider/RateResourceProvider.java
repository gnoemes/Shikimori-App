package com.gnoemes.shikimoriapp.presentation.view.anime.provider;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;

import java.util.List;

public interface RateResourceProvider {

    List<String> getAnimeRateStasuses();

    List<String> getMangaRatesWithCount();

    List<String> getMangaRateStasuses();

    List<String> getAnimeRatesWithCount();

    String getLocalizedStatus(Type type, RateStatus status);
}
