package com.gnoemes.shikimoriapp.di.menu;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.menu.MenuPresenter;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = BaseChildFragmentModule.class)
public interface MenuModule {

    @Provides
    static MenuPresenter provideMenuPresenter(UserSettingsInteractor interactor) {
        return new MenuPresenter(interactor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomChildScope
    Fragment bindFragment(MenuFragment menuFragment);
}
