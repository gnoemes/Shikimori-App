package com.gnoemes.shikimoriapp.di;

import com.gnoemes.shikimoriapp.di.auth.AuthModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.di.main.module.MainModule;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;

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
}
