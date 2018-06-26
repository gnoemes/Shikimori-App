package com.gnoemes.shikimoriapp.di.main.module;

import android.support.v7.app.AppCompatActivity;

import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.bottom.BottomTabModule;
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {BaseActivityModule.class,
        MainPresenterModule.class,
        MainUtilsModule.class})
public interface MainModule {

    @BottomScope
    @ContributesAndroidInjector(modules = BottomTabModule.class)
    BottomTabContainer fragmentMenuFragmentContainerInjector();

    @Binds
    @ActivityScope
    AppCompatActivity bindAppCompatActivity(MainActivity mainActivity);
}
