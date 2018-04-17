package com.gnoemes.shikimoriapp.di.main.module;

import com.gnoemes.shikimoriapp.di.main.scope.BottomScope;
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.favorite.FavoriteFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentInjectionModule {

    @ContributesAndroidInjector(modules = {})
    abstract FavoriteFragment favoriteFragmentInjector();

    @BottomScope
    @ContributesAndroidInjector(modules = {})
    abstract BottomTabContainer bottomTabContainerInjector();
}
