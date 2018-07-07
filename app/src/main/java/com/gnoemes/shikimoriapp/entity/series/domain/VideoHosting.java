package com.gnoemes.shikimoriapp.entity.series.domain;

public enum VideoHosting {
    SIBNET("sibnet"),
    SMOTRET_ANIME("smotret-anime"),
    VK("vk"),
    OK("ok"),
    MAIL_RU("mailru"),
    SOVET_ROMANTICA("sovetromantica"),
    MY_VI("myvi"),
    ANIMEDIA("animedia"),
    RUTUBE("rutube"),;

    private final String type;

    VideoHosting(String type) {
        this.type = type;
    }

    public boolean isEqualType(String otherType) {
        return this.type.equals(otherType);
    }

    public String getType() {
        return type;
    }
}
