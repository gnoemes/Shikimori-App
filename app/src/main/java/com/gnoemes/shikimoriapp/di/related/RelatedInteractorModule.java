package com.gnoemes.shikimoriapp.di.related;

import com.gnoemes.shikimoriapp.domain.related.RelatedInteractor;
import com.gnoemes.shikimoriapp.domain.related.RelatedInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface RelatedInteractorModule {

    @Binds
    RelatedInteractor bindRelatedInteractor(RelatedInteractorImpl interactor);
}
