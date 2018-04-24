package com.gnoemes.shikimoriapp.di.search;

import com.gnoemes.shikimoriapp.domain.search.SearchInteractor;
import com.gnoemes.shikimoriapp.domain.search.SearchInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface SearchInteractorModule {

    @Binds
    SearchInteractor bindSearchInteractor(SearchInteractorImpl searchInteractor);
}
