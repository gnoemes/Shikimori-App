package com.gnoemes.shikimoriapp.data.local.db.converters;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.EpisodeDAO;
import com.gnoemes.shikimoriapp.entity.series.domain.Episode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EpisodeDAOConverterImpl implements EpisodeDAOConverter {

    @Inject
    public EpisodeDAOConverterImpl() {
    }

    @Override
    public List<EpisodeDAO> convertTo(List<Episode> episodes) {
        List<EpisodeDAO> daos = new ArrayList<>();

        for (Episode episode : episodes) {
            daos.add(EpisodeDAO.newEpisode(
                    episode.getId(),
                    episode.getAnimeId(),
                    episode.isWatched()));
        }

        return daos;
    }

    @Override
    public List<Episode> convertFrom(List<EpisodeDAO> daoList) {
        List<Episode> episodes = new ArrayList<>();

        for (EpisodeDAO dao : daoList) {
            episodes.add(new Episode(dao.getId(),
                    dao.getAnimeId(),
                    null,
                    null,
                    null,
                    dao.getWatched() == 1));
        }
        return episodes;
    }

    private AnimeType convertType(String type) {
        for (AnimeType animeType : AnimeType.values()) {
            if (animeType.equalsType(type)) {
                return animeType;
            }
        }
        return AnimeType.NONE;
    }
}
