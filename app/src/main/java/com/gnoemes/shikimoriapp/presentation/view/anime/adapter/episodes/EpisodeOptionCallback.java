package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;

public interface EpisodeOptionCallback {

    void onAction(EpisodeOptionAction action, EpisodeItem episodeId);
}
