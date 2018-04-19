package com.gnoemes.shikimoriapp.di.calendar;

import com.gnoemes.shikimoriapp.data.repository.calendar.CalendarRepository;
import com.gnoemes.shikimoriapp.data.repository.calendar.CalendarRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CalendarRepositoryModule {

    @Binds
    CalendarRepository bindCalendarRepository(CalendarRepositoryImpl repository);
}
