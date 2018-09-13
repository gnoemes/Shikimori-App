package com.gnoemes.shikimoriapp.di.manga;

import com.gnoemes.shikimoriapp.domain.manga.MangaInteractor;
import com.gnoemes.shikimoriapp.domain.manga.MangaInteractorImpl;
import com.gnoemes.shikimoriapp.domain.ranobe.RanobeInteractor;
import com.gnoemes.shikimoriapp.domain.ranobe.RanobeInteractorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

@Module
public interface MangaInteractorModule {

    @Binds
    @Reusable
    MangaInteractor bindMangaInteractor(MangaInteractorImpl interactor);

    @Binds
    @Reusable
    RanobeInteractor bindRanobeInteractor(RanobeInteractorImpl interactor);
}
