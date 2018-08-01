package com.gnoemes.shikimoriapp.di.userhistory;

import com.gnoemes.shikimoriapp.presentation.presenter.userhistory.converter.UserHistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.userhistory.converter.UserHistoryViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface UserHistoryUtilModule {

    @Binds
    UserHistoryViewModelConverter bindHistoryViewModelConverter(UserHistoryViewModelConverterImpl converter);
}
