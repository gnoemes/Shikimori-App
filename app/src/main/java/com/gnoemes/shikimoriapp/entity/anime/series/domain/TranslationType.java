package com.gnoemes.shikimoriapp.entity.anime.series.domain;

import android.support.annotation.Nullable;

public enum TranslationType {
    VOICE_RU("озвучка"),
    SUB_RU("субтитры"),
    RAW("оригинал"),
    ALL(null);

    @Nullable
    private final String type;

    TranslationType(@Nullable String type) {
        this.type = type;
    }

    public boolean isEqualType(@Nullable String otherType) {
        return (otherType != null && type != null && type.equals(otherType))
                || (otherType == null && type == null);
    }

    @Nullable
    public String getType() {
        return type;
    }
}
