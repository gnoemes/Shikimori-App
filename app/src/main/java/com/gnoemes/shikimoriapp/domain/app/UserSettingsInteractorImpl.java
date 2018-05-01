package com.gnoemes.shikimoriapp.domain.app;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
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
}
