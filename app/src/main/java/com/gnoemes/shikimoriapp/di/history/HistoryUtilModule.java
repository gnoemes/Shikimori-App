package com.gnoemes.shikimoriapp.di.history;

import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface HistoryUtilModule {

    @Binds
    HistoryViewModelConverter bindHistoryViewModelConverter(HistoryViewModelConverterImpl converter);
}
