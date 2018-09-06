package com.gnoemes.shikimoriapp.di.manga;

import com.gnoemes.shikimoriapp.data.repository.manga.MangaRepository;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.ranobe.RanobeRepository;
import com.gnoemes.shikimoriapp.data.repository.ranobe.RanobeRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

@Module
public interface MangaRepositoryModule {

    @Binds
    @Reusable
    MangaRepository bindMangaRepository(MangaRepositoryImpl mangaRepository);

    @Binds
    @Reusable
    RanobeRepository bindRanobeRepository(RanobeRepositoryImpl repository);
}
