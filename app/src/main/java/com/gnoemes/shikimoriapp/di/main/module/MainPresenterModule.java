package com.gnoemes.shikimoriapp.di.main.module;

import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.presenter.main.MainPresenter;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

@Module
public interface MainPresenterModule {

    @Provides
    @MainActivityScope
    static MainPresenter bindMainPresenter(Router router) {
        return new MainPresenter(router);
    }
}
