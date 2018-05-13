package com.gnoemes.shikimoriapp.data.repository.app.impl;

import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenSource;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import javax.inject.Inject;

import io.reactivex.Completable;

public class TokenRepositoryImpl implements TokenRepository {

    private TokenSource source;
    private Token token;

    @Inject
    public TokenRepositoryImpl(TokenSource source) {
        this.source = source;
    }

    @Override
    public Completable saveToken(Token token) {
        this.token = token;
        return source.saveToken(token);
    }

    @Override
    public Token getToken() {
        if (token == null) {
            token = source.getToken();
            return token;
        }
        return token;
    }

    @Override
    public boolean isTokenExists() {
        return source.isTokenExists();
    }
}
