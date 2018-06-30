package com.gnoemes.shikimoriapp.di.characters;

import com.gnoemes.shikimoriapp.domain.roles.CharacterInteractor;
import com.gnoemes.shikimoriapp.domain.roles.CharacterInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CharacterInteractorModule {

    @Binds
    CharacterInteractor bindCharacterInteractor(CharacterInteractorImpl interactor);
}
