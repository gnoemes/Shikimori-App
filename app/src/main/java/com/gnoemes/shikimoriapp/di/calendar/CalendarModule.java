package com.gnoemes.shikimoriapp.di.calendar;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module(includes = {BaseChildFragmentModule.class,
        CalendarPresenterModule.class, CalendarRepositoryModule.class,
        CalendarUtilsModule.class, CalendarInteractorModule.class})
public interface CalendarModule {

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(CalendarFragment calendarFragment);
}
