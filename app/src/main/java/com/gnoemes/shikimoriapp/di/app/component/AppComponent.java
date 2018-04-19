package com.gnoemes.shikimoriapp.di.app.component;

import com.gnoemes.shikimoriapp.App;
import com.gnoemes.shikimoriapp.di.app.module.ActivityInjectionModule;
import com.gnoemes.shikimoriapp.di.app.module.AppModule;
import com.gnoemes.shikimoriapp.di.app.module.NavigationModule;
import com.gnoemes.shikimoriapp.di.app.module.UtilModule;
import com.gnoemes.shikimoriapp.di.app.module.network.ApiModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class,
        ActivityInjectionModule.class, NavigationModule.class,
        UtilModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }
}
