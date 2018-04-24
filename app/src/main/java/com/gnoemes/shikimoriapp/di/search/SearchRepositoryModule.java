package com.gnoemes.shikimoriapp.di.search;

import com.gnoemes.shikimoriapp.data.repository.search.SearchRepository;
import com.gnoemes.shikimoriapp.data.repository.search.SearchRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface SearchRepositoryModule {
    @Binds
    SearchRepository bindSearchRepository(SearchRepositoryImpl searchRepository);
}
