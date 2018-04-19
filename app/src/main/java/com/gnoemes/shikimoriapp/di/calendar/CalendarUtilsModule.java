package com.gnoemes.shikimoriapp.di.calendar;

import com.gnoemes.shikimoriapp.data.repository.calendar.converter.CalendarResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.calendar.converter.CalendarResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter.CalendarViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter.CalendarViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider.CalendarResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider.CalendarResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CalendarUtilsModule {

    @Binds
    CalendarResponseConverter bindCalendarResponseConverter(CalendarResponseConverterImpl converter);

    @Binds
    CalendarResourceProvider bindCalendarResourceProvider(CalendarResourceProviderImpl provider);

    @Binds
    CalendarViewModelConverter bindCalendarViewModelConverter(CalendarViewModelConverterImpl converter);

    @Binds
    CalendarAnimeResourceProvider bindCalendarAnimeResourceProvider(CalendarAnimeResourceProviderImpl provider);
}
