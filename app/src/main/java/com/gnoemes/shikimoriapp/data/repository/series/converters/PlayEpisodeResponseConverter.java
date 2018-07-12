package com.gnoemes.shikimoriapp.data.repository.series.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;

import java.util.List;

public interface PlayEpisodeResponseConverter {

    List<PlayEpisode> convertListOfResponsesWithSubs(String json, String subtitles);
}
