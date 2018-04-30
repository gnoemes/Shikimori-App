package com.gnoemes.shikimoriapp.data.db.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.data.db.EpisodeDAO;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import java.util.List;

public interface EpisodeDAOConverter {

    List<EpisodeDAO> convertTo(List<Episode> episodes);

    List<Episode> convertFrom(List<EpisodeDAO> daoList);
}
