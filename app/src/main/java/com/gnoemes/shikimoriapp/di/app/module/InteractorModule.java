package com.gnoemes.shikimoriapp.di.app.module;

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

}
