package com.gnoemes.shikimoriapp.di.main.module;

import android.content.Context;

import com.gnoemes.shikimoriapp.di.main.scope.ActivityContext;
import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

@Module(includes = AndroidInjectionModule.class)
public interface MainContextModule {

    @Binds
    @ActivityContext
    @MainActivityScope
    Context bindContext(BaseActivity activity);
}
