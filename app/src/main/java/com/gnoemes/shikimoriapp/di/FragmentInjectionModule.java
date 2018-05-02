package com.gnoemes.shikimoriapp.di;

import com.gnoemes.shikimoriapp.di.anime.AnimeDetailsScope;
import com.gnoemes.shikimoriapp.di.anime.AnimeModule;
import com.gnoemes.shikimoriapp.di.anime.SeriesModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarInteractorModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarPresenterModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarRepositoryModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarScope;
import com.gnoemes.shikimoriapp.di.calendar.CalendarUtilsModule;
import com.gnoemes.shikimoriapp.di.menu.MenuModule;
import com.gnoemes.shikimoriapp.di.search.FilterModule;
import com.gnoemes.shikimoriapp.di.search.SearchInteractorModule;
import com.gnoemes.shikimoriapp.di.search.SearchPresenterModule;
import com.gnoemes.shikimoriapp.di.search.SearchRepositoryModule;
import com.gnoemes.shikimoriapp.di.search.SearchScope;
import com.gnoemes.shikimoriapp.di.search.SearchUtilModule;
import com.gnoemes.shikimoriapp.di.translations.TranslationsModule;
import com.gnoemes.shikimoriapp.di.translations.TranslationsScope;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarFragment;
import com.gnoemes.shikimoriapp.presentation.view.favorite.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentInjectionModule {

    @ContributesAndroidInjector(modules = {})
    FavoriteFragment favoriteFragmentInjector();

    @ContributesAndroidInjector(modules = {})
    BottomTabContainer bottomTabContainerInjector();

    @CalendarScope
    @ContributesAndroidInjector(modules = {CalendarPresenterModule.class, CalendarRepositoryModule.class,
            CalendarUtilsModule.class, CalendarInteractorModule.class})
    CalendarFragment calendarFragmentInjector();

    @SearchScope
    @ContributesAndroidInjector(modules = {SearchPresenterModule.class, SearchRepositoryModule.class,
            SearchInteractorModule.class, SearchUtilModule.class})
    SearchFragment searchFragmentInjector();

    @ContributesAndroidInjector(modules = {FilterModule.class})
    FilterDialogFragment filterFragmentInjector();

    @AnimeDetailsScope
    @ContributesAndroidInjector(modules = {AnimeModule.class, SeriesModule.class})
    AnimeFragment animeFragmentInjector();

    @TranslationsScope
    @ContributesAndroidInjector(modules = {TranslationsModule.class, SeriesModule.class})
    TranslationsFragment translationsFragmentInjector();

    @ContributesAndroidInjector(modules = {MenuModule.class})
    MenuFragment menuFragmentInjector();
}
