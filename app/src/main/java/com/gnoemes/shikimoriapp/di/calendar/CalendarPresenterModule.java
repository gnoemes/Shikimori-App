package com.gnoemes.shikimoriapp.di.calendar;

import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.CalendarPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter.CalendarViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider.CalendarResourceProvider;

import dagger.Module;
import dagger.Provides;

@Module
public interface CalendarPresenterModule {

    @Provides
    static CalendarPresenter bindCalendarPresenter(CalendarInteractor calendarInteractor,
                                                   CalendarResourceProvider resourceProvider,
                                                   CalendarViewModelConverter converter) {
        return new CalendarPresenter(calendarInteractor, resourceProvider, converter);
    }
}
