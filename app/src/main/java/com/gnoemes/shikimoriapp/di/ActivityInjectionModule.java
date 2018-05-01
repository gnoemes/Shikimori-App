package com.gnoemes.shikimoriapp.di;

import com.gnoemes.shikimoriapp.di.main.module.MainContextModule;
import com.gnoemes.shikimoriapp.di.main.module.MainPresenterModule;
import com.gnoemes.shikimoriapp.di.main.module.MainUtilsModule;
import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityInjectionModule {

    @MainActivityScope
    @ContributesAndroidInjector(modules = {MainContextModule.class, MainPresenterModule.class,
            MainUtilsModule.class, FragmentInjectionModule.class})
    MainActivity mainActivityInjector();

}
