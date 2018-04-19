package com.gnoemes.shikimoriapp.data.repository.calendar;

import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;

import java.util.List;

import io.reactivex.Single;

public interface CalendarRepository {

    /**
     * Returns calendar items
     */
    Single<List<CalendarItem>> getCalendarData();
}
