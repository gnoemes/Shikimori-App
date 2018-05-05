package com.gnoemes.shikimoriapp.domain.auth;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AuthInteractorImpl implements AuthInteractor {

    private TokenRepository tokenRepository;
    private UserSettingsRepository settingsRepository;
    private AuthorizationRepository authorizationRepository;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public AuthInteractorImpl(@NonNull TokenRepository tokenRepository,
                              @NonNull AuthorizationRepository authorizationRepository,
                              @NonNull UserSettingsRepository settingsRepository,
                              @NonNull CompletableErrorHandler completableErrorHandler,
                              @NonNull RxUtils rxUtils) {
        this.tokenRepository = tokenRepository;
        this.authorizationRepository = authorizationRepository;
        this.settingsRepository = settingsRepository;
        this.completableErrorHandler = completableErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Completable signIn(@NonNull String authCode) {
        return authorizationRepository.signIn(authCode)
                .flatMapCompletable(token -> tokenRepository.saveToken(token))
                .toSingleDefault(new UserSettings.Builder())
                .map(builder -> builder.setStatus(UserStatus.AUTHORIZED).build())
                .flatMapCompletable(userSettings -> settingsRepository.saveUserSettings(userSettings))
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
