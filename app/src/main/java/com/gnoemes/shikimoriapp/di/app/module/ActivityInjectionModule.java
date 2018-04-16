package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.di.main.module.MainContextModule;
import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityInjectionModule {

    @MainActivityScope
    @ContributesAndroidInjector(modules = {MainContextModule.class})
    abstract MainActivity mainActivityInjector();

}
