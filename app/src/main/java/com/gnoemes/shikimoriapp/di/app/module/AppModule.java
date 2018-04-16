package com.gnoemes.shikimoriapp.di.app.module;

import android.content.Context;

import com.gnoemes.shikimoriapp.App;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
public interface AppModule {

    @Binds
    @Singleton
    Context bindContext(App app);

}
