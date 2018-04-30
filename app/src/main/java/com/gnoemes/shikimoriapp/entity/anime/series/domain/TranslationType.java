package com.gnoemes.shikimoriapp.entity.anime.series.domain;

public enum TranslationType {
    VOICE_RU("voiceRu"),
    VOICE_EN("voiceEn"),
    SUB_RU("subRu"),
    SUB_EN("subEn"),
    RAW("raw");


    private final String type;

    TranslationType(String type) {
        this.type = type;
    }

    public boolean equalType(String otherType) {
        return type.equals(otherType);
    }

    public String getType() {
        return type;
    }
}
