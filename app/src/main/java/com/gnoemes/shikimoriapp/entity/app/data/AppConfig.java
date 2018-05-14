package com.gnoemes.shikimoriapp.entity.app.data;

public class AppConfig {

    public static final String AUTH_URL = "https://shikimori.org/oauth/authorize?" +
            "client_id=f6f9ff07c7fdca024c5d3395f6dc8d9e802bda458a213d5c382d5d6e69bc77b0&" +
            "redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&response_type=code";
    /**
     * Default timeout for connections
     */
    public static final int DEFAULT_TIMEOUT = 10;

    /**
     * Default limit for pagination
     */
    public static final int DEFAULT_LIMIT = 12;

    /**
     * Long timeout for connections
     */
    public static final int LONG_TIMEOUT = 30;

    /**
     * Auto dark theme starts at
     */
    public static final int AUTO_THEME_START_HOURS = 20;

    /**
     * Auto dark theme ends at
     */
    public static final int AUTO_THEME_END_HOURS = 8;
}
