package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter;

import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;

public interface EpisodeOptionCallback {
    void onAction(EpisodeOptionAction action, EpisodeItem episodeId);
}
