package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import android.support.annotation.Nullable;

public enum PlayerType {
    EMBEDDED("embedded"),
    EXTERNAL("external"),
    WEB("browser"),;

    private final String type;

    PlayerType(String type) {
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
