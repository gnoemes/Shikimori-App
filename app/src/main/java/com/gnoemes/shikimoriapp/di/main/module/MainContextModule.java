package com.gnoemes.shikimoriapp.di.main.module;

import android.content.Context;

import com.gnoemes.shikimoriapp.di.main.scope.ActivityContext;
import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainContextModule {

    @Binds
    @ActivityContext
    @MainActivityScope
    Context bindContext(MainActivity activity);
}
