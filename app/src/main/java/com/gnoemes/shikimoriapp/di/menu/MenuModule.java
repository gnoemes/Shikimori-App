package com.gnoemes.shikimoriapp.di.menu;

import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.menu.MenuPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public interface MenuModule {

    @Provides
    static MenuPresenter provideMenuPresenter(UserSettingsInteractor interactor) {
        return new MenuPresenter(interactor);
    }
}
