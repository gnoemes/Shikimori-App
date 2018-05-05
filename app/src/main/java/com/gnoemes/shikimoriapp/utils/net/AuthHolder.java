package com.gnoemes.shikimoriapp.utils.net;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AuthHolder {

    private TokenRepository tokenRepository;
    private AuthorizationRepository authorizationRepository;

    public AuthHolder(TokenRepository tokenRepository, AuthorizationRepository authorizationRepository) {
        this.tokenRepository = tokenRepository;
        this.authorizationRepository = authorizationRepository;
    }


    @NonNull
    public Token getToken() {
        return tokenRepository.getToken();
    }

    public void refresh() {
        updateToken().subscribe();
    }

    private Completable updateToken() {
        return Single.just(getToken())
                .flatMap(token -> authorizationRepository.refreshToken(token.getRefreshToken()))
                .flatMapCompletable(token -> tokenRepository.saveToken(token));
    }
}
