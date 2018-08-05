package com.gnoemes.shikimoriapp.di.alternative;


import com.gnoemes.shikimoriapp.data.repository.alternative.AlternativeRepository;
import com.gnoemes.shikimoriapp.data.repository.alternative.AlternativeRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AlternativeRepositoryModule {

    @Binds
    AlternativeRepository bindAlternativeRepository(AlternativeRepositoryImpl repository);

}
