package com.gnoemes.shikimoriapp.domain.app;

import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;

import javax.inject.Inject;

import io.reactivex.Completable;

public class LogoutInteractorImpl implements LogoutInteractor {

    private TokenRepository tokenRepository;
    private UserSettingsRepository settingsRepository;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public LogoutInteractorImpl(TokenRepository tokenRepository,
                                UserSettingsRepository settingsRepository,
                                CompletableErrorHandler completableErrorHandler,
                                RxUtils rxUtils) {
        this.tokenRepository = tokenRepository;
        this.settingsRepository = settingsRepository;
        this.completableErrorHandler = completableErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Completable logout() {
        return tokenRepository.saveToken(null)
                .andThen(settingsRepository.saveUserSettings(new UserSettings.Builder()
                        .setStatus(UserStatus.GUEST)
                        .setUserBrief(null)
                        .build()
                ))
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
