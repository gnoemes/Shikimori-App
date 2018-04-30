package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EpisodeViewModelConverterImpl implements EpisodeViewModelConverter {

    @Inject
    public EpisodeViewModelConverterImpl() {

    }

    @Override
    public List<BaseEpisodeItem> apply(List<Episode> episodes) {
        List<BaseEpisodeItem> items = new ArrayList<>();

        String episodeFull = null;
        long episodeId = 0;
        boolean isFirst = true;

        boolean flag = true;

        //TODO wrong logic
        for (Episode episode : episodes) {
            items.add(convertEpisode(episode));
            if (!episode.isWatched() && flag) {
                episodeFull = episode.getEpisodeFull();
                episodeId = episode.getId();
                flag = false;
            }
            if (episode.isWatched()) {
                isFirst = false;
            }
        }

        if (!items.isEmpty()) {
            episodeId = episodeId == 0 ? episodes.get(episodes.size() - 1).getId() : episodeId;
            episodeFull = episodeFull == null ? episodes.get(episodes.size() - 1).getEpisodeFull() : episodeFull;
            items.add(0, new EpisodeOptionsItem(episodeId, isFirst, episodeFull));
        }
        return items;
    }

    private EpisodeItem convertEpisode(Episode episode) {
        return new EpisodeItem(episode.getId(),
                episode.getSeriesId(),
                episode.getAnimeId(),
                episode.getEpisodeFull(),
                episode.getEpisode(),
                episode.getType(),
                episode.getDate(),
                episode.getViews(),
                episode.isWatched());
    }
}
