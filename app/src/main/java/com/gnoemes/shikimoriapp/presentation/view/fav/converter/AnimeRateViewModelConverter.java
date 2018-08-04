package com.gnoemes.shikimoriapp.presentation.view.fav.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;

import java.util.List;

public interface AnimeRateViewModelConverter {

    List<BaseItem> convertFrom(List<AnimeRate> rates);
}
