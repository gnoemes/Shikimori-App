package com.gnoemes.shikimoriapp.utils.net;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

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
        Disposable disposable = updateToken().subscribe(() -> {
        }, this::processErrors);
    }

    private void processErrors(Throwable throwable) {
        if (throwable instanceof ServiceCodeException) {

        }
    }

    private Completable updateToken() {
        return Single.just(getToken())
                .flatMap(token -> authorizationRepository.refreshToken(token.getRefreshToken()))
                .flatMapCompletable(token -> tokenRepository.saveToken(token));
    }
}
