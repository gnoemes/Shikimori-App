package com.gnoemes.shikimoriapp.entity.app.domain;

public enum AnalyticsEvent {
    ANIME_OPENED("anime_opened"),
    PROFILE_OPENED("profile_opened"),
    SETTINGS_OPENED("settings_opened"),
    FILTER_OPENED("filter_opened"),
    RATE_OPENED("rate_opened"),
    WATCH_ONLINE_CLICKED("watch_online_clicked"),
    WATCH_ONLINE_MASTER_CLICKED("watch_online_master_clicked"),
    FAV_RATE_CHANGED("fav_rate_changed"),
    MANUAL_SEARCH("manual_search"),
    USER_HISTORY_CLICKED("user_history_clicked"),
    CALENDAR_UPDATED("calendar_updated"),
    TRANSLATIONS_OPENED("translations_opened"),
    GENRE_SEARCH("genre_search"),
    WEB_PLAYER_OPENED("web_player_opened"),
    SIMILAR_CLICKED("similar_clicked"),
    ACCOUNT_EXIT("account_exit"),
    EMBEDDED_PLAYER_OPENED("embedded_player_opened"),
    FOUR_PDA_CLICKED("4pda_topic_opened"),
    SHIKIMORI_APP_CLUB_CLICKED("shikimori_app_club_clicked");

    private final String event;

    AnalyticsEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
