package com.gnoemes.shikimoriapp.di.history;

import com.gnoemes.shikimoriapp.domain.history.HistoryInteractor;
import com.gnoemes.shikimoriapp.domain.history.HistoryInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface HistoryInteractorModule {

    @Binds
    HistoryInteractor bindHistoryInteractor(HistoryInteractorImpl interactor);
}
