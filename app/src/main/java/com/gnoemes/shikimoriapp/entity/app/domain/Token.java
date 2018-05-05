package com.gnoemes.shikimoriapp.entity.app.domain;

public class Token {
    private String authToken;
    private String refreshToken;

    public Token(String authToken, String refreshToken) {
        this.authToken = authToken;
        this.refreshToken = refreshToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
