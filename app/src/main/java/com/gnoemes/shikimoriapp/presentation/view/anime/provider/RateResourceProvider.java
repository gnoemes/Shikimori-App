package com.gnoemes.shikimoriapp.presentation.view.anime.provider;

import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;

import java.util.List;

public interface RateResourceProvider {

    List<String> getRateStasuses();

    String getLocalizedStatus(RateStatus status);
}
