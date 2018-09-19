package com.gnoemes.shikimoriapp.domain.app;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class UserSettingsInteractorImpl implements UserSettingsInteractor {

    private UserSettingsRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public UserSettingsInteractorImpl(@NonNull UserSettingsRepository repository,
                                      @NonNull SingleErrorHandler singleErrorHandler,
                                      @NonNull CompletableErrorHandler completableErrorHandler,
                                      @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.completableErrorHandler = completableErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Observable<UserSettings> getUserSettings() {
        return repository.getUserSettings()
//                .compose((SingleErrorHandler<UserSettings>) singleErrorHandler)
                .compose(rxUtils.applySchedulers());
    }

    @Override
    public Completable saveUserSettings(UserSettings settings) {
        return repository.saveUserSettings(settings)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public UserBrief getUser() {
        return repository.getUser();
    }

    @Override
    public void setUser(UserBrief user) {
        repository.setUser(user);
    }

    @Override
    public void clearUser() {
        repository.clearUser();
    }

    @Override
    public UserStatus getUserStatus() {
        return repository.getUserStatus();
    }

    @Override
    public void setUserStatus(UserStatus status) {
        repository.setUserStatus(status);
    }

    @Override
    public Boolean isAutoStatusEnabled() {
        return repository.isAutoStatusEnabled();
    }

    @Override
    public void isAutoStatusEnabled(Boolean status) {
        repository.isAutoStatusEnabled(status);
    }

    @Override
    public Boolean isRomadziNaming() {
        return repository.isRomadziNaming();
    }

    @Override
    public void isRomadziNaming(Boolean enabled) {
        repository.isRomadziNaming(enabled);
    }

    @Override
    public Boolean isRememberType() {
        return repository.isRememberType();
    }

    @Override
    public void isRememberType(Boolean enabled) {
        repository.isRememberType(enabled);
    }

    @Override
    public TranslationType getTranslationType() {
        return repository.getTranslationType();
    }

    @Override
    public void setTranslationType(TranslationType type) {
        repository.setTranslationType(type);
    }

    @Override
    public Boolean isNotificationsEnabled() {
        return repository.isNotificationsEnabled();
    }
}
