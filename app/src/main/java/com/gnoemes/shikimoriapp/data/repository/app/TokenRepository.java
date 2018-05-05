package com.gnoemes.shikimoriapp.data.repository.app;

import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import io.reactivex.Completable;

public interface TokenRepository {

    /**
     * Save token
     */
    Completable saveToken(Token token);

    /**
     * Get token
     */
    Token getToken();

    /**
     * Check is token exists
     */
    boolean isTokenExists();
}
