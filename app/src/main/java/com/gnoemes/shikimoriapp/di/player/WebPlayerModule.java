package com.gnoemes.shikimoriapp.di.player;


import android.support.v7.app.AppCompatActivity;

import com.gnoemes.shikimoriapp.di.anime.SeriesModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.player.WebPlayerPresenter;
import com.gnoemes.shikimoriapp.presentation.view.player.WebPlayerActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseActivityModule.class, SeriesModule.class})
public interface WebPlayerModule {

    @Provides
    static WebPlayerPresenter provideWebPlayerPresenter(SeriesInteractor interactor) {
        return new WebPlayerPresenter(interactor);
    }

    @Binds
    @ActivityScope
    AppCompatActivity bindAppCompatActivity(WebPlayerActivity activity);
}
