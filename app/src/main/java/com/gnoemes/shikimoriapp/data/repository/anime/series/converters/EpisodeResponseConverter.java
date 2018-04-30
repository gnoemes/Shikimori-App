package com.gnoemes.shikimoriapp.data.repository.anime.series.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.data.network.EpisodeResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

public interface EpisodeResponseConverter {

    Episode convertFrom(EpisodeResponse response, long animeId);
}
