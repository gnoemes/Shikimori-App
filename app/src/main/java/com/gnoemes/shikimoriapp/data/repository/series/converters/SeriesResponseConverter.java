package com.gnoemes.shikimoriapp.data.repository.series.converters;

import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import org.jsoup.nodes.Document;

public interface SeriesResponseConverter {
    Series apply(long animeId, Document doc) throws Exception;
}
