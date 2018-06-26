package com.gnoemes.shikimoriapp.entity.rates.presentation;

import com.gnoemes.shikimoriapp.entity.rates.domain.PlaceholderType;

public class AnimeRatePlaceholder extends BaseAnimeRateItem {
    private PlaceholderType type;

    public AnimeRatePlaceholder(PlaceholderType type) {
        this.type = type;
    }

    public PlaceholderType getType() {
        return type;
    }
}
