package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction;

public class DetailsActionItem extends BaseAnimeItem {
    private DetailsAction action;

    public DetailsActionItem(DetailsAction action) {
        this.action = action;
    }

    public DetailsAction getAction() {
        return action;
    }
}
