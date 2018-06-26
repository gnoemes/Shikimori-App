package com.gnoemes.shikimoriapp.di.player;

import android.support.v7.app.AppCompatActivity;

import com.gnoemes.shikimoriapp.di.anime.SeriesModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.player.EmbeddedPlayerPresenter;
import com.gnoemes.shikimoriapp.presentation.view.player.embedded.EmbeddedPlayerActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseActivityModule.class, SeriesModule.class})
public interface EmbeddedPlayerModule {

    @Provides
    static EmbeddedPlayerPresenter provideEmbeddedPlayerPresenter(SeriesInteractor interactor) {
        return new EmbeddedPlayerPresenter(interactor);
    }

    @Binds
    @ActivityScope
    AppCompatActivity bindAppCompatActivity(EmbeddedPlayerActivity activity);
}
