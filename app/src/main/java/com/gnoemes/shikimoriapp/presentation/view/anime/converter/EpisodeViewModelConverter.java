package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import java.util.List;

import io.reactivex.functions.Function;

public interface EpisodeViewModelConverter extends Function<List<Episode>, List<BaseEpisodeItem>> {
}
