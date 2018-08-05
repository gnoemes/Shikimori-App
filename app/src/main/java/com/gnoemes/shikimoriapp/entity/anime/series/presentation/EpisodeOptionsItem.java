package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;

public class EpisodeOptionsItem extends BaseEpisodeItem {
    private boolean isFirst;
    private EpisodeItem episodeItem;

    public EpisodeOptionsItem(boolean isFirst, EpisodeItem episodeItem) {
        this.isFirst = isFirst;
        this.episodeItem = episodeItem;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public EpisodeItem getEpisodeItem() {
        return episodeItem;
    }
}
