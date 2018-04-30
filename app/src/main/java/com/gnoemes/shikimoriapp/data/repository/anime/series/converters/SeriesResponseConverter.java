package com.gnoemes.shikimoriapp.data.repository.anime.series.converters;

import com.gnoemes.shikimoriapp.entity.anime.data.SeriesDataResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Series;

import io.reactivex.functions.Function;

public interface SeriesResponseConverter extends Function<SeriesDataResponse, Series> {
}
