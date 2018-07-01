package com.gnoemes.shikimoriapp.di.screenshots;

import android.support.v7.app.AppCompatActivity;

import com.gnoemes.shikimoriapp.di.anime.AnimeModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.screenshots.ScreenshotsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.screenshots.ScreenshotsActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseActivityModule.class, AnimeModule.class})
public interface ScreenshotsModule {

    @Provides
    static ScreenshotsPresenter provideScreenshotsPresenter(AnimeInteractor interactor) {
        return new ScreenshotsPresenter(interactor);
    }

    @Binds
    @ActivityScope
    AppCompatActivity bindAppCompatActivity(ScreenshotsActivity screenshotsActivity);
}
