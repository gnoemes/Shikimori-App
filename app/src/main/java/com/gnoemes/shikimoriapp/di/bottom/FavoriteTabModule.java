package com.gnoemes.shikimoriapp.di.bottom;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.presentation.view.bottom.FavoriteFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.favorite.FavoriteFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = BaseFragmentModule.class)
public interface FavoriteTabModule {

    @BottomChildScope
    @ContributesAndroidInjector(modules = {})
    FavoriteFragment favoriteFragmentInjector();

    @Binds
    @BottomScope
    @Named(BaseFragmentModule.FRAGMENT)
    Fragment bindFragment(FavoriteFragmentContainer tabContainer);
}
