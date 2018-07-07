package com.gnoemes.shikimoriapp.entity.series.domain;

public enum VideoHosting {
    SIBNET("sibnet", "sibnet.ru"),
    SMOTRET_ANIME("smotret-anime", "smotret-anime.ru"),
    VK("vk", "vk.com"),
    OK("ok", "ok.ru"),
    MAIL_RU("mailru", "mail.ru"),
    SOVET_ROMANTICA("sovetromantica", "sovetromantica.com"),
    MY_VI("myvi", "myvi.ru"),
    ANIMEDIA("animedia", "animedia.tv"),
    RUTUBE("rutube", "rutube.ru"),
    YOUTUBE("youtube", "youtube.com"),
    UNKNOWN("", "");

    private final String type;
    private final String synonymType;

    VideoHosting(String type, String synonymType) {
        this.type = type;
        this.synonymType = synonymType;
    }

    public boolean isEqualType(String otherType) {
        return this.type.equals(otherType) || this.synonymType.equals(otherType);
    }

    public String getType() {
        return type;
    }

    public String getSynonymType() {
        return synonymType;
    }
}
