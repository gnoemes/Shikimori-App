package com.gnoemes.shikimoriapp.data.repository.alternative.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.alternative.converters.EpisodeResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.EpisodeResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import javax.inject.Inject;

public class EpisodeResponseConverterImpl implements EpisodeResponseConverter {

    @Inject
    public EpisodeResponseConverterImpl() {
    }

    @Override
    public Episode convertFrom(EpisodeResponse response, long animeId) {
        return new Episode(response.getId(),
                response.getSeriesId(),
                animeId,
                response.getEpisodeFull(),
                response.getEpisode(),
                covertType(response.getType()),
                null,
                response.getViews(),
                false);
    }

    private AnimeType covertType(String type) {
        try {
            return AnimeType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
