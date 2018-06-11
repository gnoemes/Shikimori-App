package com.gnoemes.shikimoriapp.di.related;

import com.gnoemes.shikimoriapp.data.repository.related.RelatedRepository;
import com.gnoemes.shikimoriapp.data.repository.related.RelatedRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface RelatedRepositoryModule {

    @Binds
    RelatedRepository bindRelatedRepository(RelatedRepositoryImpl repository);
}
