package com.gnoemes.shikimoriapp.data.repository.alternative.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.alternative.converters.EpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.data.SeriesDataResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.EpisodeResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.SeriesResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Series;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SeriesResponseConverterImpl implements SeriesResponseConverter {

    private EpisodeResponseConverter converter;

    @Inject
    public SeriesResponseConverterImpl(EpisodeResponseConverter converter) {
        this.converter = converter;
    }

    @Override
    public Series apply(SeriesDataResponse dataResponse) {
        SeriesResponse response = dataResponse.getSeriesResponses().get(0);

        return new Series(response.getId(),
                response.getAnimeId(),
                response.getEpisodesCount(),
                convertEpisodes(response.getEpisodes(), response.getAnimeId()));
    }

    private List<Episode> convertEpisodes(List<EpisodeResponse> episodes, long animeId) {
        List<Episode> episodeList = new ArrayList<>();

        if (episodes == null) {
            return episodeList;
        }

        for (EpisodeResponse response : episodes) {
            episodeList.add(converter.convertFrom(response, animeId));
        }
        return episodeList;
    }
}
