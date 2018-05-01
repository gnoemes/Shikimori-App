package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import android.support.annotation.Nullable;

public enum TranslationDubberSettings {
    AUTO("auto"),
    MANUAL("manual"),;

    private final String type;

    TranslationDubberSettings(String type) {
        this.type = type;
    }

    public boolean isEqualType(@Nullable String otherType) {
        return otherType != null && type.equals(otherType);
    }

    @Override
    public String toString() {
        return type;
    }
}
