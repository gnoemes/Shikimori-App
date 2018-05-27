package com.gnoemes.shikimoriapp.di.bottom;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.fav.FavoriteModule;
import com.gnoemes.shikimoriapp.di.history.HistoryModule;
import com.gnoemes.shikimoriapp.di.menu.MenuModule;
import com.gnoemes.shikimoriapp.di.profile.ProfileModule;
import com.gnoemes.shikimoriapp.presentation.view.bottom.MenuFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = BaseFragmentModule.class)
public interface MenuTabModule {

    @BottomChildScope
    @ContributesAndroidInjector(modules = {FavoriteModule.class})
    FavoriteFragment favoriteFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = MenuModule.class)
    MenuFragment fragmentMenuFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {ProfileModule.class})
    ProfileFragment profileFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = HistoryModule.class)
    HistoryFragment historyFragmentInjector();

    @Binds
    @BottomScope
    @Named(BaseFragmentModule.FRAGMENT)
    Fragment bindFragment(MenuFragmentContainer container);
}

