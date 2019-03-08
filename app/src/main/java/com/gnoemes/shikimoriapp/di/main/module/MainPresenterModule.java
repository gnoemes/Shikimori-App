package com.gnoemes.shikimoriapp.di.main.module;

import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.domain.notifications.JobSchedulingInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.main.MainPresenter;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

@Module
public interface MainPresenterModule {

    @Provides
    @ActivityScope
    static MainPresenter bindMainPresenter(Router router,
                                           JobSchedulingInteractor schedulingInteractor) {
        return new MainPresenter(router, schedulingInteractor);
    }
}
