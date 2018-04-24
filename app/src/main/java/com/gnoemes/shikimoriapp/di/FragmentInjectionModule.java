package com.gnoemes.shikimoriapp.di;

import com.gnoemes.shikimoriapp.di.calendar.CalendarInteractorModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarPresenterModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarRepositoryModule;
import com.gnoemes.shikimoriapp.di.calendar.CalendarUtilsModule;
import com.gnoemes.shikimoriapp.di.main.scope.BottomScope;
import com.gnoemes.shikimoriapp.di.search.FilterModule;
import com.gnoemes.shikimoriapp.di.search.SearchInteractorModule;
import com.gnoemes.shikimoriapp.di.search.SearchPresenterModule;
import com.gnoemes.shikimoriapp.di.search.SearchRepositoryModule;
import com.gnoemes.shikimoriapp.di.search.SearchUtilModule;
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarFragment;
import com.gnoemes.shikimoriapp.presentation.view.favorite.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentInjectionModule {

    @ContributesAndroidInjector(modules = {})
    abstract FavoriteFragment favoriteFragmentInjector();

    @BottomScope
    @ContributesAndroidInjector(modules = {})
    abstract BottomTabContainer bottomTabContainerInjector();

    @ContributesAndroidInjector(modules = {CalendarPresenterModule.class, CalendarRepositoryModule.class,
            CalendarUtilsModule.class, CalendarInteractorModule.class})
    abstract CalendarFragment calendarFragmentInjector();

    @ContributesAndroidInjector(modules = {SearchPresenterModule.class, SearchRepositoryModule.class,
            SearchInteractorModule.class, SearchUtilModule.class})
    abstract SearchFragment searchFragmentInjector();

    @ContributesAndroidInjector(modules = {FilterModule.class})
    abstract FilterDialogFragment filterFragmentInjector();
}
