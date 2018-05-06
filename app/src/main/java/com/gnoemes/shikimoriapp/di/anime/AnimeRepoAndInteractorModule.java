package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepositoryImpl;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AnimeRepoAndInteractorModule {

    @Binds
    AnimeRepository bindAnimeRepository(AnimeRepositoryImpl repository);

    @Binds
    AnimeInteractor bindAnimeDetailsInteractor(AnimeInteractorImpl interactor);
}
