package com.gnoemes.shikimoriapp.utils.net.shiki;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class AuthHolder {

    private TokenRepository tokenRepository;
    private UserSettingsRepository settingsRepository;
    private AuthorizationRepository authorizationRepository;

    public AuthHolder(TokenRepository tokenRepository,
                      AuthorizationRepository authorizationRepository,
                      UserSettingsRepository settingsRepository) {
        this.tokenRepository = tokenRepository;
        this.authorizationRepository = authorizationRepository;
        this.settingsRepository = settingsRepository;
    }


    @Nullable
    public Token getToken() {
        return tokenRepository.getToken();
    }

    public void refresh() {
        Disposable disposable = updateToken().subscribe(() -> {
        }, this::processErrors);
    }

    private void processErrors(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            if (exception.code() == HttpStatusCode.UNAUTHORISED) {
                settingsRepository.clearUser();
            }
        }
    }

    private Completable updateToken() {
        Token token = getToken();

        if (token == null || TextUtils.isEmpty(token.getRefreshToken())) {
            return Completable.fromAction(() -> settingsRepository.clearUser());
        }

        return Single.just(token)
                .flatMap(tkn -> authorizationRepository.refreshToken(tkn.getRefreshToken()))
                .flatMapCompletable(tkn -> tokenRepository.saveToken(tkn));
    }
}
