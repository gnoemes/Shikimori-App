package com.gnoemes.shikimoriapp.di.alternative;

import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor;
import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AlternativeInteractorModule {

    @Binds
    AlternativeSeriesInteractor bindAlternativeSeriesInteractor(AlternativeSeriesInteractorImpl interactor);
}
