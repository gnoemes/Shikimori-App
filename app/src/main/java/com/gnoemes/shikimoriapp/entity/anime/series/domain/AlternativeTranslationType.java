package com.gnoemes.shikimoriapp.entity.anime.series.domain;

import android.support.annotation.Nullable;

public enum AlternativeTranslationType {
    VOICE_RU("voiceRu"),
    SUB_RU("subRu"),
    RAW("raw"),
    VOICE_EN("voiceEn"),
    SUB_EN("subEn"),
    ALL(null);

    @Nullable
    private final String type;

    AlternativeTranslationType(@Nullable String type) {
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
