package com.gnoemes.shikimoriapp.di.main.module;

import android.support.v7.app.AppCompatActivity;

import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.di.base.scopes.ActivityScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.bottom.CalendarTabModule;
import com.gnoemes.shikimoriapp.di.bottom.FavoriteTabModule;
import com.gnoemes.shikimoriapp.di.bottom.MenuTabModule;
import com.gnoemes.shikimoriapp.di.bottom.SearchTabModule;
import com.gnoemes.shikimoriapp.di.bottom.SocialTabModule;
import com.gnoemes.shikimoriapp.presentation.view.bottom.CalendarFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.FavoriteFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.MenuFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.SearchFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.SocialFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {BaseActivityModule.class,
        MainPresenterModule.class,
        MainUtilsModule.class})
public interface MainModule {

    @BottomScope
    @ContributesAndroidInjector(modules = FavoriteTabModule.class)
    FavoriteFragmentContainer fragmentFavoriteContainerInjector();

    @BottomScope
    @ContributesAndroidInjector(modules = CalendarTabModule.class)
    CalendarFragmentContainer fragmentCalendarContainerInjector();

    @BottomScope
    @ContributesAndroidInjector(modules = SearchTabModule.class)
    SearchFragmentContainer fragmentSearchContainerInjector();

    @BottomScope
    @ContributesAndroidInjector(modules = SocialTabModule.class)
    SocialFragmentContainer fragmentSocialFragmentContainerInjector();

    @BottomScope
    @ContributesAndroidInjector(modules = MenuTabModule.class)
    MenuFragmentContainer fragmentMenuFragmentContainerInjector();

    @Binds
    @ActivityScope
    AppCompatActivity bindAppCompatActivity(MainActivity mainActivity);
}
