package com.gnoemes.shikimoriapp.di.characters;

import com.gnoemes.shikimoriapp.data.repository.roles.CharacterRepository;
import com.gnoemes.shikimoriapp.data.repository.roles.CharacterRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CharacterRepositoryModule {

    @Binds
    CharacterRepository bindCharacterRepository(CharacterRepositoryImpl repository);
}
