package com.gnoemes.shikimoriapp.di.person;

import com.gnoemes.shikimoriapp.domain.roles.PersonInteractor;
import com.gnoemes.shikimoriapp.domain.roles.PersonInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface PersonInteractorModule {

    @Binds
    PersonInteractor bindPersonInteractor(PersonInteractorImpl interactor);
}
