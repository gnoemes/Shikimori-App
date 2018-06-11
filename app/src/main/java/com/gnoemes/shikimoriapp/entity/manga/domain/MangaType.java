package com.gnoemes.shikimoriapp.entity.manga.domain;

public enum MangaType {
    MANGA("manga"),
    MANHWA("manhwa"),
    MANHUA("manhua"),
    NOVEL("novel"),
    ONE_SHOT("one_shot"),
    DOUJIN("doujin");

    private final String type;

    MangaType(String type) {
        this.type = type;
    }

    public boolean equalsType(String otherType) {
        return type.equals(otherType);
    }

    @Override
    public String toString() {
        return this.type;
    }
}
