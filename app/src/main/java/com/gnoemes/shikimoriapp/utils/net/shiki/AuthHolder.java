package com.gnoemes.shikimoriapp.utils.net.shiki;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;

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
                settingsRepository.saveUserSettings(new UserSettings.Builder()
                        .setUserBrief(null)
                        .setStatus(UserStatus.GUEST)
                        .build())
                        .subscribe();
            }
        }
    }

    private Completable updateToken() {
        Token token = getToken();

        if (token == null) {
            return Completable.complete();
        }

        return Single.just(token)
                .flatMap(tkn -> authorizationRepository.refreshToken(tkn.getRefreshToken()))
                .flatMapCompletable(tkn -> tokenRepository.saveToken(tkn));
    }
}
