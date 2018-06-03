package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractorImpl;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractorImpl;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractorImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface InteractorModule {

    @Binds
    @Singleton
    UserInteractor bindUserInteractor(UserInteractorImpl interactor);

    @Binds
    @Singleton
    UserRatesInteractor bindUserRatesInteractor(UserRatesInteractorImpl userRatesInteractor);

    @Binds
    @Singleton
    AnalyticsInteractor bindAnalyticsInteractor(AnalyticsInteractorImpl interactor);
}
