package com.gnoemes.shikimoriapp.data.repository.calendar;

import com.gnoemes.shikimoriapp.data.network.CalendarApi;
import com.gnoemes.shikimoriapp.data.repository.calendar.converter.CalendarResponseConverter;
import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class CalendarRepositoryImpl implements CalendarRepository {

    private CalendarApi calendarApi;
    private CalendarResponseConverter responseConverter;

    @Inject
    public CalendarRepositoryImpl(@NonNull CalendarApi calendarApi,
                                  @NonNull CalendarResponseConverter responseConverter) {
        this.calendarApi = calendarApi;
        this.responseConverter = responseConverter;
    }

    @Override
    public Single<List<CalendarItem>> getCalendarData() {
        return calendarApi.getCalendarData()
                .map(responseConverter);
    }
}
