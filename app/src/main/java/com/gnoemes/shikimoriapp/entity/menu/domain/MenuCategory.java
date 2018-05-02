package com.gnoemes.shikimoriapp.entity.menu.domain;

public enum MenuCategory {
    PROFILE(0),
    NEWS(1),
    NOTIFICATIONS(2),
    MESSAGES(3),
    FRIENDS(4),
    SETTINGS(5),

    //TODO SOCIAL LINKS/SOMETHING MORE
    ;

    /**
     * Position in list
     */
    private final int position;

    MenuCategory(int position) {
        this.position = position;
    }

    public boolean isEqualPosition(int position) {
        return this.position == position;
    }

    public int getPosition() {
        return position;
    }
}
