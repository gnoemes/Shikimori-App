package com.gnoemes.shikimoriapp.di.auth;

import com.gnoemes.shikimoriapp.domain.auth.AuthInteractor;
import com.gnoemes.shikimoriapp.domain.auth.AuthInteractorImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.auth.AuthPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface AuthModule {

    @Provides
    static AuthPresenter provideAuthPresenter(AuthInteractor authInteractor) {
        return new AuthPresenter(authInteractor);
    }

    @Binds
    AuthInteractor bindAuthInteractor(AuthInteractorImpl interactor);

}
