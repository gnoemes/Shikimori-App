package com.gnoemes.shikimoriapp.domain.calendar;

import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Interactor for calendar fragment
 */
public interface CalendarInteractor {

    /**
     * Get calendar items
     *
     * @return <List<CalendarItem>>
     */
    Single<List<CalendarItem>> getCalendarData();

    /**
     * Get my calendar items
     */
    Single<List<CalendarItem>> getMyCalendarData();
}
