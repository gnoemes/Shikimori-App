package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;

import java.util.List;

import io.reactivex.functions.Function;

public interface AlternativeEpisodeViewModelConverter extends Function<List<Episode>, List<BaseItem>> {

}
