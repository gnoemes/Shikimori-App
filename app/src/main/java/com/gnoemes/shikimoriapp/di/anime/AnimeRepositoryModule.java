package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AnimeRepositoryModule {

    @Binds
    AnimeRepository bindAnimeRepository(AnimeRepositoryImpl repository);
}
