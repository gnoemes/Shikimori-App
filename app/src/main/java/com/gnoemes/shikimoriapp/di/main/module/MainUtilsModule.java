package com.gnoemes.shikimoriapp.di.main.module;

import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainUtilsModule {

    @Binds
    @MainActivityScope
    MainResourceProvider bindMainResourceProvider(MainResourceProviderImpl provider);
}
