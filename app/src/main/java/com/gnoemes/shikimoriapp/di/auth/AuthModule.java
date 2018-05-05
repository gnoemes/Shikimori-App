package com.gnoemes.shikimoriapp.di.auth;

import android.support.v7.app.AppCompatActivity;

import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.domain.auth.AuthInteractor;
import com.gnoemes.shikimoriapp.domain.auth.AuthInteractorImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.auth.AuthPresenter;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = BaseActivityModule.class)
public interface AuthModule {

    @Provides
    static AuthPresenter provideAuthPresenter(AuthInteractor authInteractor) {
        return new AuthPresenter(authInteractor);
    }

    @Binds
    AuthInteractor bindAuthInteractor(AuthInteractorImpl interactor);

    @Binds
    @ActivityScope
    AppCompatActivity bindAppCompatActivity(AuthActivity authActivity);
}
