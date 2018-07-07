package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import java.util.List;

import io.reactivex.functions.Function;

public interface EpisodeViewModelConverter extends Function<Series, List<BaseEpisodeItem>> {
}
