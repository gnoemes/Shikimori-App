package com.gnoemes.shikimoriapp.entity.forum.domain;

public enum ForumType {
    ALL("all"),
    NEWS("news"),
    ANIME_AND_MANGA("animanga"),
    VISUAL_NOVELS("vn"),
    GAMES("games"),
    SITE("site"),
    OFF_TOPIC("offtopic"),
    CLUBS("clubs"),
    REVIEWS("reviews"),
    CONTESTS("contests"),
    COLLECTIONS("collections"),
    COSPLAY("cosplay"),
    MY_CLUBS("my_clubs");

    private final String type;

    ForumType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean isEqualType(String otherType) {
        return this.type.equals(otherType);
    }
}
