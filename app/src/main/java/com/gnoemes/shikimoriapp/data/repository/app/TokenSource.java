package com.gnoemes.shikimoriapp.data.repository.app;

import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import io.reactivex.Completable;

public interface TokenSource {

    Completable saveToken(Token token);

    Token getToken();

    boolean isTokenExists();
}
