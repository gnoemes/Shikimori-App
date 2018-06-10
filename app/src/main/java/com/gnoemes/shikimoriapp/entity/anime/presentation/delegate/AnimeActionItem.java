package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;

public class AnimeActionItem extends BaseAnimeItem {
    private AnimeAction action;

    public AnimeActionItem(AnimeAction action) {
        this.action = action;
    }

    public AnimeAction getAction() {
        return action;
    }
}
