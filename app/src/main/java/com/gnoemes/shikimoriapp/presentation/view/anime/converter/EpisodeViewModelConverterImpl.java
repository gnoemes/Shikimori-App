package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeErrorItem;
import com.gnoemes.shikimoriapp.entity.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EpisodeViewModelConverterImpl implements EpisodeViewModelConverter {

    @Inject
    public EpisodeViewModelConverterImpl() {

    }

    @Override
    public List<BaseEpisodeItem> apply(Series series) {
        List<BaseEpisodeItem> items = new ArrayList<>();

        boolean isFirst = true;
        EpisodeItem currentItem = null;

        boolean flag = true;

        //TODO wrong logic
        for (Episode episode : series.getEpisodes()) {
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
            currentItem = currentItem == null ? convertEpisode(series.getEpisodes().get(series.getEpisodes().size() - 1)) : currentItem;
            items.add(0, new EpisodeOptionsItem(isFirst, currentItem));
        }

        if (series.isHasError()) {
            items.add(new EpisodeErrorItem(series.getErrorMessage()));
        }

        return items;
    }

    private EpisodeItem convertEpisode(Episode episode) {
        return new EpisodeItem(episode.getId(),
                episode.getAnimeId(),
                episode.getRawHostings(),
                episode.isWatched());
    }
}
