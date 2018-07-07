package com.gnoemes.shikimoriapp.di;

import com.gnoemes.shikimoriapp.di.auth.AuthModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.di.main.module.MainModule;
import com.gnoemes.shikimoriapp.di.player.EmbeddedPlayerModule;
import com.gnoemes.shikimoriapp.di.player.WebPlayerModule;
import com.gnoemes.shikimoriapp.di.screenshots.ScreenshotsModule;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;
import com.gnoemes.shikimoriapp.presentation.view.player.WebPlayerActivity;
import com.gnoemes.shikimoriapp.presentation.view.player.embedded.EmbeddedPlayerActivity;
import com.gnoemes.shikimoriapp.presentation.view.screenshots.ScreenshotsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityInjectionModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class})
    MainActivity mainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = AuthModule.class)
    AuthActivity authActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = EmbeddedPlayerModule.class)
    EmbeddedPlayerActivity embeddedPlayerActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = WebPlayerModule.class)
    WebPlayerActivity webPlayerActivtityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = ScreenshotsModule.class)
    ScreenshotsActivity screenshotsActivityInjector();
}
