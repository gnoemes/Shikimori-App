package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AnimeInteractorModule {

    @Binds
    AnimeInteractor bindAnimeDetailsInteractor(AnimeInteractorImpl interactor);
}
