package com.gnoemes.shikimoriapp.presentation.view.fav.converter;

import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;

import java.util.List;

public interface AnimeRateViewModelConverter {

    List<BaseAnimeRateItem> convertFrom(List<AnimeRate> rates);
}
