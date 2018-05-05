package com.gnoemes.shikimoriapp.di.bottom;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.menu.MenuModule;
import com.gnoemes.shikimoriapp.presentation.view.bottom.MenuFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = BaseFragmentModule.class)
public interface MenuTabModule {

    @BottomChildScope
    @ContributesAndroidInjector(modules = MenuModule.class)
    MenuFragment fragmentMenuFragmentInjector();

    @Binds
    @BottomScope
    @Named(BaseFragmentModule.FRAGMENT)
    Fragment bindFragment(MenuFragmentContainer container);
}

