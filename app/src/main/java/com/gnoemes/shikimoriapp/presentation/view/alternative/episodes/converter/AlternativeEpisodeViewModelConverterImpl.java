package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AlternativeEpisodeViewModelConverterImpl implements AlternativeEpisodeViewModelConverter {

    @Inject
    public AlternativeEpisodeViewModelConverterImpl() {
    }

    @Override
    public List<BaseItem> apply(List<Episode> episodes) {
        List<BaseItem> items = new ArrayList<>();

        boolean isFirst = true;
        EpisodeItem currentItem = null;

        boolean flag = true;

        for (Episode episode : episodes) {
            EpisodeItem item = convertEpisode(episode);
            items.add(item);
            if (!episode.isWatched() && flag) {
                currentItem = item;
                flag = false;
            }
            if (episode.isWatched()) {
                isFirst = false;
            }
        }

        if (!items.isEmpty()) {
            currentItem = currentItem == null ? convertEpisode(episodes.get(episodes.size() - 1)) : currentItem;
            items.add(0, new EpisodeOptionsItem(isFirst, currentItem));
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
