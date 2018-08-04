package com.gnoemes.shikimoriapp.di.person;

import com.gnoemes.shikimoriapp.data.repository.roles.PersonRepository;
import com.gnoemes.shikimoriapp.data.repository.roles.PersonRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface PersonRepositoryModule {

    @Binds
    PersonRepository bindPersonRepository(PersonRepositoryImpl repository);
}
