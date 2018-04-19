package com.gnoemes.shikimoriapp.di.calendar;

import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor;
import com.gnoemes.shikimoriapp.domain.calendar.impl.CalendarInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CalendarInteractorModule {

    @Binds
    CalendarInteractor bindCalendarInteractor(CalendarInteractorImpl calendarInteractor);
}
